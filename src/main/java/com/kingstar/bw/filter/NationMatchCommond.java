package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: meitao
 * @Description: 字符串匹配
 * @Date: 20-8-26 下午2:35
 * @Version: 1.0
 */
@Service
public class NationMatchCommond extends MatchCommand{

    /**
     * 匹配国家,返回大于等于精准度的
     * @param context
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(Context context) throws Exception {

        ChainContext chainContext = this.convert(context);

        Search search = chainContext.getSearch();
        List<String> values = this.getValue(search.getId(),Constant.KEY_NATION);
        BigDecimal rate = new BigDecimal(0);
        BigDecimal tarRate = new BigDecimal(0);
        for (String nation: values){
            //当为空,rate为0
            if (!StringUtils.isEmpty(search.getNation())) {
                //自然人
                if (search.isPer()) {
                    //当输入项不为空,黑名单为空为50%
                    if (StringUtils.isEmpty(nation)) {
                        rate = BigDecimal.valueOf(0.5);
                    }

                } else {
                    //机构
                    //当输入项不等于黑名单0%
                    if (search.getNation().equals(nation)) {
                        rate = BigDecimal.valueOf(0);
                    }

                }
                //输入项等于黑名单数据100%
                if (search.getNation().equals(nation)) {
                    rate = BigDecimal.valueOf(1);
                }
                //当输入项不等于黑名单-1000%
                if (search.getNation().equals(nation)) {
                    rate = BigDecimal.valueOf(-10);
                }
            }
            //取id列表中最大匹配的值
            if (tarRate.compareTo(rate)<1||StringUtils.isEmpty(search.getNation())){
                tarRate = rate;
                search.setNation(nation);
            }
        }

        //证件号匹配度大于等于设置的匹配度
        return this.isEnd(chainContext, tarRate);
    }
}
