package com.kingstar.bw.commond;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: 性别匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class GenderMatchCommond extends MatchCommand {


    @Override
    public boolean execute(Context context) throws Exception {

        ChainContext chainContext = this.convert(context);

        Search search = chainContext.getSearch();
        Search tarSearch = this.getTarget(search.getId());
        BigDecimal rate = new BigDecimal(0);
        BigDecimal tarRate = new BigDecimal(-10);
        //当黑名单证件号为空 50%
        if (tarSearch == null) {
            tarRate = BigDecimal.valueOf(0.5);
        }
        //当为空,rate为0
        if (!StringUtils.isEmpty(search.getGender())) {
            //当黑名单证件号为空 50%
            if (StringUtils.isEmpty(tarSearch.getGender())) {
                rate = BigDecimal.valueOf(0.5);
            } else if (tarSearch.getGender().equals(search.getGender())) {
                //当性别匹配
                rate = BigDecimal.valueOf(1);
            } else {
                //当性别不匹配
                rate = BigDecimal.valueOf(-10);
            }
        }
        //取id列表中最大匹配的值
        if (tarRate.compareTo(rate) < 0 || StringUtils.isEmpty(search.getGender())) {
            tarRate = rate;
            //将匹配的性别返回
            search.setGender(tarSearch.getGender());
        }
        //证件号匹配度大于等于设置的匹配度
        return this.isEnd(chainContext, tarRate);
    }

    @Override
    public String getDisplay() {
        return Constant.DS_GENDER;
    }
}
