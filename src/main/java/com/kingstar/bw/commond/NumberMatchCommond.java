package com.kingstar.bw.commond;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import com.kingstar.bw.exception.PlatException;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: meitao
 * @Description: 字符串匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class NumberMatchCommond extends MatchCommand {

    @Override
    public boolean execute(Context context) throws Exception {

        ChainContext chainContext = this.convert(context);

        Search search = chainContext.getSearch();
        BigDecimal rate = new BigDecimal(0);
        BigDecimal tarRate = new BigDecimal(0);

        List<String> numbers = this.getValue(search.getId(), Constant.KEY_NUMBER_ID);
        String retrunNumber ="";
        //当为空,rate为0
        if (search.getNumber() != null) {
            if (numbers.isEmpty()) {
                //当黑名单证件号为空 50%
                tarRate = BigDecimal.valueOf(0.5);
            }
            for (String number : numbers) {
                //当黑名单证件号为空 50%
                if (StringUtils.isEmpty(number)) {
                    continue;
                }
                //都不为空时做匹配
                rate = LevenshteinDistance.computeLevenshteinDistanceRate(search.getNumber(), number);
                //取id列表中最大匹配的值
                if (tarRate.compareTo(rate) < 0 || StringUtils.isEmpty(search.getNumber())) {
                    tarRate = rate;
                    //将匹配的证件号返回
                    retrunNumber = number;
//                    search.setNumber(number);
                }
            }
        }
        search.setNumber(retrunNumber);
        //证件号匹配度大于等于设置的匹配度
        return this.isEnd(chainContext, tarRate);
    }

    @Override
    public String getDisplay() {
        return Constant.DS_NUMBER;
    }

    /**
     * 获取key值对应列表
     *
     * @param id
     * @param key
     * @return
     */
    public List<String> getValue(String id, String key) {
        if (StringUtils.isEmpty(id)) {
            throw new PlatException(" id 不能为空!");
        }
        if (StringUtils.isEmpty(key)) {
            throw new PlatException(" key 不能为空!");
        }
        //获取内存数据库中的所有的数据
        Map<String, List<String>> allParm = LocalData.getCollection(key);
        //黑名单中的目标对象
        List<String> tars = allParm.get(id);

        if (tars == null) {
            throw new PlatException("黑名单中没有" + id + key + "对应的数据!");
        }

        return tars;
    }
}
