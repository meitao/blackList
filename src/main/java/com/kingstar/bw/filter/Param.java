package com.kingstar.bw.filter;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: 设置参数接口
 * @Date: 20-8-27 下午6:03
 * @Version: 1.0
 */
public interface Param {

    /**
     * 设置参数
     * @param param
     */
    public void set(BigDecimal param);


    /**
     * 获取参数
     * @return
     */
    public BigDecimal get();



}
