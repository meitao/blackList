package com.kingstar.bw.bean;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-28 上午9:05
 * @Version: 1.0
 */
public class Params {

    //匹配度
    private BigDecimal rate ;

    //权重值
    private BigDecimal weight;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
