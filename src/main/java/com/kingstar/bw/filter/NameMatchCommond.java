package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.exception.PlatException;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: meitao
 * @Description: 字符串匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class NameMatchCommond extends MatchCommand {

    /**
     * 匹配名称的字符串,返回大于等于精准度的
     *
     * @return
     */

    @Override
    public boolean execute(Context context) throws Exception {

        ChainContext chainContext = this.convert(context);

        Search search = chainContext.getSearch();

        List<Search> tarSearchs = this.getTarget(search.getId());
        BigDecimal rate = new BigDecimal(0);
        BigDecimal tarRate = new BigDecimal(0);
        for (Search tarSearch : tarSearchs) {
            //名称匹配度大于等于设置的匹配度
            rate = LevenshteinDistance.computeLevenshteinDistanceRate(search.getName(), tarSearch.getName());
            //取id列表中最大匹配的值
            if (tarRate.compareTo(rate) < 0||StringUtils.isEmpty(search.getName())) {
                tarRate = rate;
                search.setName(tarSearch.getName());
            }
        }


        return this.isEnd(chainContext, tarRate);
    }

}
