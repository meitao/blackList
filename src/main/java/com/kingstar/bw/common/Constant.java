package com.kingstar.bw.common;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: 常量类
 * @Date: 20-8-26 下午2:30
 * @Version: 1.0
 */
public class Constant {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_NAME_ID = "NAME_ID";
    public static final String KEY_BIRTHDAY = "BIRTHDAY";
    public static final String KEY_NATION = "NATION";
    public static final String KEY_NUMBER = "NUMBER";
    public static final String KEY_NUMBER_ID = "NUMBER_ID";
    public static final String KEY_ALL = "ALL";

    //精准度 现设置为固定值,可做配置化
    public static final BigDecimal PERCISION = new BigDecimal("0.8");
    //黑名单中为空时的匹配度
    public static final BigDecimal LISTISNULL_PERCISION = new BigDecimal("0.5");


    //权重信息
    public static final BigDecimal NAME_NUM_WEIGHT = new BigDecimal("1");
    public static final BigDecimal NAME_WEIGHT = new BigDecimal("0.5");

    public static final BigDecimal NUMBER_WEIGHT = new BigDecimal("0.5");
    public static final BigDecimal GENDER_WEIGHT = new BigDecimal("0.05");
    public static final BigDecimal NATION_WEIGHT =  new BigDecimal("0.05");
    public static final BigDecimal ADDR_WEIGHT =  new BigDecimal("0.05");
    public static final BigDecimal BRITHDAY_WEIGHT = new BigDecimal("0.05");


    public static final int INIT_FETCH_SIZE = 100000;
}
