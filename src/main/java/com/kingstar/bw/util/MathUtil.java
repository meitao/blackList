package com.kingstar.bw.util;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.exception.PlatException;
import com.kingstar.bw.bean.Params;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

/**
 * @Author: meitao
 * @Description: 计算工具类
 * @Date: 20-8-28 上午9:40
 * @Version: 1.0
 */
public class MathUtil {
    /**
     * 计算加权
     * @param chainContext
     */
    public static void rateSum(ChainContext chainContext){
        //判断加权匹配度是否大于准确率 p*w1+p2*w2 ..../w1 + w2...
        Map<String, Params> paramList  =chainContext.getParamList();
        Set<Map.Entry<String, Params>> set =paramList.entrySet();
        if (set.isEmpty()){
            throw  new PlatException("值不能为空!");
        }
        //分子的总和
        BigDecimal sum = new BigDecimal(0);
        //分母
        BigDecimal sumMo = new BigDecimal(0);

        for (Map.Entry<String, Params> entry:set){
            Params params =  entry.getValue();
            sum = sum.add(params.getRate())  ;
            sumMo = sumMo.add(params.getWeight());
        }
        chainContext.setSumRate(sum.divide(sumMo,2, RoundingMode.HALF_UP));
        chainContext.setEndRate(chainContext.getSumRate());
    }

    /**
     * 计算加权
     * @param chainContext
     */
    public static void rateSum(ChainContext chainContext,BigDecimal rate,BigDecimal heavy){
        //判断加权匹配度是否大于准确率 p*w1+p2*w2 ..../w1 + w2...
        Map<String, Params> paramList  =chainContext.getParamList();
        Set<Map.Entry<String, Params>> set =paramList.entrySet();
        //分子的总和
        BigDecimal sum = new BigDecimal(0).add(rate);
        //分母
        BigDecimal sumMo = new BigDecimal(0).add(heavy);
        for (Map.Entry<String, Params> entry:set){
            Params params =  entry.getValue();
            sum.add(params.getRate())  ;
            sumMo.add(params.getWeight());
        }
        chainContext.setSumRate(sum.divide(sumMo,2, RoundingMode.HALF_UP));
        chainContext.setEndRate(chainContext.getSumRate());
    }

}
