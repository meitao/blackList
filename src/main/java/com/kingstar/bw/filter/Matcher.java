package com.kingstar.bw.filter;

import com.kingstar.bw.bean.Search;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: meitao
 * @Description: 字符串匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
public interface Matcher {

    /**
     * 匹配结果
     * @param search
     * @return
     */
    public List<String> match(Search search);


    /**
     * 设置匹配结果的条数
     * @param num
     */
    public  void setMatchNum(int num);



}
