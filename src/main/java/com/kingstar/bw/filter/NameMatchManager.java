package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: meitao
 * @Description: 匹配逻辑的的流程总控
 * @Date: 20-8-27 下午4:12
 * @Version: 1.0
 */
@Service
public class NameMatchManager implements MatchManager {

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 当证件号码为空，从姓名的匹配开始调用，在执行匹配连
     *
     * @param chainContext
     * @return
     */
    @Override
    public List<ChainContext> match(ChainContext chainContext) {

        Search search = chainContext.getSearch();
        //判断名称和证件号码是否有一个存在,有则继续匹配,没有则报错
//        if (StringUtils.isEmpty(search.getNumber()) && StringUtils.isEmpty(search.getName())) {
//            throw new PlatException("名称和证件号码都为空!");
//        }
        //当名称和证件号码都有,先匹配证件号,优先过滤数据多的,两项都存在的权重值和只有一项的权重值不一样
        //当证件号没有,匹配姓名
        //获取内存数据库中的名称信息
        Map<String, String> param = LocalData.getCollection(Constant.KEY_NAME);
        //保存返回结果
        List<ChainContext> result = new ArrayList<ChainContext>();


        //姓名最短编辑距离匹配
        Set<Map.Entry<String, String>> set = param.entrySet();
        for (Map.Entry<String, String> entry : set) {


            //名称匹配度大于等于设置的匹配度
            BigDecimal matchRate = LevenshteinDistance.computeLevenshteinDistanceRate(search.getName(), entry.getKey());
            //匹配度大于精准度,根据匹配的结果区分机构或个人
            if (matchRate.compareTo(Constant.PERCISION) > -1) {

                //拷贝
                ChainContext coChainContext = new ChainContext();
                Search search1 = new Search();
                search1.setAddr(search.getAddr());
                search1.setBirthDay(search.getBirthDay());
                search1.setNumber(search.getNumber());
                search1.setPer(search.isPer());
                search1.setGender(search.getGender());
                search1.setNation(search.getNation());
                search1.setId(entry.getKey());
                coChainContext.setSearch(search1);

                Map<String, Params> paramsMap = coChainContext.getParamList();
                Params params = new Params();
                params.setWeight(Constant.NAME_NUM_WEIGHT);
                params.setRate(matchRate.multiply(Constant.NAME_NUM_WEIGHT));
                paramsMap.put("name", params);
                //设置姓名的匹配度到执行连上下文中
                coChainContext.setParamList(paramsMap);
                coChainContext.getSearch().setName(entry.getValue());

                if (search.isPer()) {
                    //个人按照证件号,姓名(排除项),国家(排除项),出生日期(排除项),地址,处理链
                    PersonMatchChain personMatchChain = new PersonMatchChain();
                    personMatchChain.init();
                    try {
                        personMatchChain.execute(coChainContext);
                    } catch (Exception e) {
                        logger.error("匹配错误!",e);
                    }
                } else {
                    //机构,证件号,国家,地址 处理链
                    OrgMatchChain orgMatchChain = new OrgMatchChain();
                    orgMatchChain.init();
                    try {
                        orgMatchChain.execute(coChainContext);
                    } catch (Exception e) {
                        logger.error("匹配错误!",e);
                    }
                }
//                if (matchRate.compareTo(Constant.PERCISION) > -1) {
                if (coChainContext.getSumRate() != null && coChainContext.getSumRate().compareTo(Constant.PERCISION) > -1) {
                    result.add(coChainContext);
                }

            }
        }
        return result;

    }


}
