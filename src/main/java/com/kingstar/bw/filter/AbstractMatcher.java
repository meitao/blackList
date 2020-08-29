package com.kingstar.bw.filter;

import com.kingstar.bw.bean.Search;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 下午2:56
 * @Version: 1.0
 */
public abstract class AbstractMatcher implements Matcher ,Param{

    //匹配度
    private BigDecimal rate ;

    //权重值
    private BigDecimal heavy ;

    private int num ;

    @Override
    public void setMatchNum(int num) {
        this.num = num;
    }

    public BigDecimal get() {
        return rate;
    }

    public int getNum() {
        return num;
    }

    public void set(BigDecimal param){
        rate = param;
    }


    public BigDecimal getHeavy() {
        return heavy;
    }

    public void setHeavy(BigDecimal heavy) {
        this.heavy = heavy;
    }
}
