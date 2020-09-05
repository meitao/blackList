package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.apache.commons.chain.Context;
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
public class NumberMatchCommond extends MatchCommand {

    @Override
    public boolean execute(Context context) throws Exception {

        ChainContext chainContext = this.convert(context);

        Search search = chainContext.getSearch();
        Search tarSearch = this.getTarget(search.getId());
        BigDecimal rate = new BigDecimal(0);
        BigDecimal tarRate = new BigDecimal(0);

        //当为空,rate为0
        if (!StringUtils.isEmpty(search.getNumber())) {
            //当黑名单证件号为空 50%
            if (StringUtils.isEmpty(tarSearch.getNumber())) {
                rate = BigDecimal.valueOf(0.5);
            } else {
                //都不为空时做匹配
                rate = LevenshteinDistance.computeLevenshteinDistanceRate(search.getNumber(), tarSearch.getNumber());
            }
        }
        //取id列表中最大匹配的值
        if (tarRate.compareTo(rate) < 0 || StringUtils.isEmpty(search.getNumber())) {
            tarRate = rate;
            //将匹配的证件号返回
            search.setNumber(tarSearch.getNumber());
        }


        //证件号匹配度大于等于设置的匹配度
        return this.isEnd(chainContext, rate);
    }
}
