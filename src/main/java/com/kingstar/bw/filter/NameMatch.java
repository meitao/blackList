package com.kingstar.bw.filter;

import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: meitao
 * @Description: 字符串匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class NameMatch   {

    /**
     * 匹配名称的字符串,返回大于等于精准度的
     *
     * @param search
     * @return

    @Override
    public List<String> match(Search search) {


        BigDecimal rate = new BigDecimal(0);
        //当为空,rate为0
        if (!StringUtils.isEmpty(search.getNumber())) {
            //当黑名单证件号为空 50%
            if (StringUtils.isEmpty(tarSearch.getNumber())) {
                rate = BigDecimal.valueOf(0.5);
            } else if (tarSearch.getGender().equals(search.getGender())) {
                //当性别匹配
                rate = BigDecimal.valueOf(1);
            } else {
                //当性别不匹配
                rate = BigDecimal.valueOf(-10);
            }
        }

        return result;
    } */
}
