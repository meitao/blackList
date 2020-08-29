package com.kingstar.bw.common;

import com.kingstar.bw.filter.MatchCommand;
import com.kingstar.bw.filter.NumberMatchCommond;
import com.kingstar.bw.filter.Params;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-29 上午10:13
 * @Version: 1.0
 */
public class CommondUtil {

    /**
     * MatchCommand设置参数
     * @param matchCommand
     * @param rate
     * @param weight
     * @return
     */
    public static MatchCommand warp(MatchCommand matchCommand, BigDecimal rate ,BigDecimal weight){
//        Params params1 = new Params();
//        params1.setRate(new BigDecimal("0.5"));
//        params1.setWeight(new BigDecimal("0.5"));
//        matchCommand.setParams(params1);
        return matchCommand;
    }
}
