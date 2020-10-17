package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Params;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import com.kingstar.bw.exception.PlatException;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: meitao
 * @Description: 证件号匹配流程控制
 * @Date: 20-8-27 下午4:12
 * @Version: 1.0
 */
@Service
public class MutilNumberMatchManager implements MatchManager {

    protected final Log logger = LogFactory.getLog(getClass());

    //多线程处理匹配
    private ExecutorService executorService =
            new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()/2, Runtime.getRuntime().availableProcessors()/2,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1000000), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("MutilNumberMatchManager-thread-" + UUID.randomUUID().toString());
                    return t;
                }
            });

    public List<ChainContext> match(ChainContext chainContext) {

        Search search = chainContext.getSearch();

        //保存返回结果
        List<ChainContext> result = new ArrayList<ChainContext>();
        //当名称和证件号码都有,先匹配证件号,优先过滤数据多的,两项都存在的权重值和只有一项的权重值不一样
        //获取内存数据库中的证件信息
        Map<String, Map<String, List<String>>> list = LocalData.getCollection(Constant.KEY_NUMBER_PER);
        //根据输入的名称长度和
        BigDecimal len = BigDecimal.valueOf(search.getNumber().length());

        BigDecimal percision = BigDecimal.valueOf(search.getPercision());
        // 进1
        double min = Math.ceil(percision.multiply(len).doubleValue());
        // 退1
        double max = Math.floor(len.divide(percision, 2, RoundingMode.HALF_UP).doubleValue());

        for (int i = (int) min; i <= max; i++) {
            Map<String, List<String>> param = list.get(String.valueOf(i));
            if (param == null) {
                continue;
            }
            Set<Map.Entry<String, List<String>>> set = param.entrySet();
            //异步转同步
            CountDownLatch countDownLatch = new CountDownLatch(set.size());

            for (Map.Entry<String, List<String>> entry : set) {
                executorService.execute(() -> {
                    BigDecimal matchRate = null;
                    try {


                        //如果黑名单中的证件号为空
                        if (StringUtils.isEmpty(entry.getKey())) {
                            matchRate = Constant.LISTISNULL_PERCISION;
                        } else {
                            //名称匹配度大于等于设置的匹配度
                            matchRate = LevenshteinDistance.computeLevenshteinDistanceRate(search.getNumber(), entry.getKey());

                            //匹配度大于精准度,根据匹配的结果区分机构或个人
                            if (matchRate.compareTo(percision) > -1) {
                                //获取名称相对应的id列表
                                List<String> ids = entry.getValue();
                                for (String id : ids) {
                                    //拷贝
                                    ChainContext coChainContext = new ChainContext();
                                    Search search1 = new Search();
                                    search1.setAddr(search.getAddr());
                                    search1.setBirthDay(search.getBirthDay());
                                    search1.setNumber(search.getNumber());
                                    search1.setName(search.getName());
                                    search1.setPer(search.isPer());
                                    search1.setGender(search.getGender());
                                    search1.setNation(search.getNation());
                                    search1.setPercision(search.getPercision());
                                    search1.setId(id);
                                    coChainContext.setSearch(search1);

                                    Map<String, Params> paramsMap = coChainContext.getParamList();
                                    Params params = new Params();
                                    //当只有一项不为空时的权重值
                                    params.setWeight(Constant.NAME_NUM_WEIGHT);
                                    params.setRate(matchRate.multiply(Constant.NAME_NUM_WEIGHT));
                                    //当名单为空时
                                    paramsMap.put(Constant.DS_NUMBER, params);
                                    //设置匹配度结果到执行连上下文中
                                    coChainContext.setParamList(paramsMap);
                                    coChainContext.setSumRate(params.getRate());
                                    coChainContext.getSearch().setNumber(entry.getKey());
                                    if (search.isPer()) {
                                        //个人按照证件号,姓名(排除项),国家(排除项),出生日期(排除项),地址,处理链
                                        PersonMatchChain personMatchChain = new PersonMatchChain();
                                        personMatchChain.init(coChainContext.getSearch());
                                        try {
                                            personMatchChain.execute(coChainContext);
                                        } catch (Exception e) {
                                            new PlatException(e);
                                        }
                                    } else {
                                        //机构,证件号,国家,地址 处理链
                                        OrgMatchChain orgMatchChain = new OrgMatchChain();
                                        orgMatchChain.init(coChainContext.getSearch());
                                        try {
                                            orgMatchChain.execute(coChainContext);
                                        } catch (Exception e) {
                                            new PlatException(e);
                                        }
                                    }
                                    if (coChainContext.getSumRate() != null && coChainContext.getSumRate().compareTo(percision) > -1) {
                                        result.add(coChainContext);
                                    }
                                }

                            }
                        }
                    } catch (Exception e) {
                        logger.error("匹配错误!", e);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        //根据匹配度降序排序
        if(!result.isEmpty()){
            result.sort(Comparator.comparing(ChainContext::getSumRate).reversed());
        }
        return result;

    }


}
