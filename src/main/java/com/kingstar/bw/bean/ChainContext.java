package com.kingstar.bw.bean;

import org.apache.commons.chain.impl.ContextBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-27 下午5:19
 * @Version: 1.0
 */
public class ChainContext extends ContextBase {
    /**
     * 输入数据
     */
    private Search search;
    /**
     * 加权匹配项的值
     */
    private BigDecimal sumRate ;

    /**
     * 最后匹配项的值
     */
    private BigDecimal endRate ;

    /**
     * 各个指标项的匹配度
     */
    private Map<String, Params> paramList = new HashMap<String,Params>();

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public BigDecimal getSumRate() {
        return sumRate;
    }

    public void setSumRate(BigDecimal sumRate) {
        this.sumRate = sumRate;
    }

    public BigDecimal getEndRate() {
        return endRate;
    }

    public void setEndRate(BigDecimal endRate) {
        this.endRate = endRate;
    }

    public Map<String, Params> getParamList() {
        return paramList;
    }

    public void setParamList(Map<String, Params> paramList) {
        this.paramList = paramList;
    }
}
