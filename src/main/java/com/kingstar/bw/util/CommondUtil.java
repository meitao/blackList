package com.kingstar.bw.util;

import com.kingstar.bw.filter.MatchCommand;
import com.kingstar.bw.filter.NumberMatchCommond;
import com.kingstar.bw.filter.Params;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 判断名字的长度获取相应的hash ,对数据进行分区
     * @param name
     * @param id
     * @param map
     */
    public static void putPatition(String name,String id ,Map<String , Map<String, List<String>>> map){
        //判断名字的长度获取相应的hash
        String len = String.valueOf(name.length());
        Map<String, List<String>> param = map.get(len);
        List<String> list = null;
        //判断map是否为空，如果为空则新建hash
        if (param == null) {
            param = new HashMap<String, List<String>>();
            list = new ArrayList<String>();
        } else {
            list = param.get(name);
            if (list == null) {
                list = new ArrayList<String>();
            }
        }
        list.add(id);
        //新增或更新name对应的值
        param.put(name, list);
        map.put(len, param);
    }

    /**
     *  将对于的id值中，将value放入列表中
     * @param id
     * @param value
     * @param map
     */
    public static void storeMap(String id,String value ,Map<String ,List<String>> map){
        List<String> list = map.get(id);
        if (list == null) {
            list = new ArrayList<String>();
        }
        list.add(value);

        //新增或更新name对应的值
        map.put(id, list);
    }

}
