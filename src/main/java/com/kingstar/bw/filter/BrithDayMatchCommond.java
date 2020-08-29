package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: meitao
 * @Description: 生日匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class BrithDayMatchCommond extends MatchCommand {

    /**
     * 返回大于等于精准度的
     *
     * @param context
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(Context context) throws Exception {

        ChainContext chainContext = this.convert(context);

        Search search = chainContext.getSearch();
        Search tarSearch = this.getTarget(search.getId());

        BigDecimal rate = new BigDecimal(0);
        //当为空,rate为0
        if (!StringUtils.isEmpty(search.getNumber())) {
            //当黑名出生日期为空 50%
            if (StringUtils.isEmpty(tarSearch.getNumber())) {
                rate = BigDecimal.valueOf(0.5);
            } else {
                //计算最短路径除以两字符的最短长度,d（src,tar）/min(src,tar)
                int distance = LevenshteinDistance.computeLevenshteinDistance_Optimized(search.getBirthDay(), tarSearch.getBirthDay());
                int min = Math.min(search.getBirthDay().length(), tarSearch.getBirthDay().length());
                BigDecimal result = new BigDecimal(min - distance);
                result.divide(BigDecimal.valueOf(min), 2, RoundingMode.HALF_UP);
            }
        }
        //将匹配的生日日期返回
        search.setBirthDay(tarSearch.getBirthDay());
        return this.isEnd(chainContext, rate);
    }

}
