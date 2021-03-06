package com.kingstar.bw.commond;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.ml.LevenshteinDistance;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
        List<String> values = this.getValue(search.getId(),Constant.KEY_BIRTHDAY);
        BigDecimal rate = new BigDecimal(0);
        BigDecimal tarRate = new BigDecimal(-100);
        String returnBir = "";
        //当黑名出生日期为空 50%
        if (values==null||values.isEmpty()){
            tarRate = BigDecimal.valueOf(0.5);
        }else{
            for(String value :values  ){
                //当为空,rate为0
                if (!StringUtils.isEmpty(search.getBirthDay())) {
                    //当黑名出生日期为空 50%
                    if (StringUtils.isEmpty(value)) {
                        rate = BigDecimal.valueOf(0.5);
                    } else {
                        //计算最短路径除以两字符的最短长度,d（src,tar）/min(src,tar)
                        int min = Math.min(search.getBirthDay().length(), value.length());
                        //如果黑明单中为8位直接匹配
                        //如果黑明单中为6位截取前面6位，那6位，8位，4位年，都是截取前面字段
                        //如果黑明单中为4位先按年份匹配再安月日数据匹配，4位需要截取后面四个字段匹配
                        int distance = LevenshteinDistance.computeLevenshteinDistance_Optimized(search.getBirthDay().substring(0,min), value.substring(0,min));
                        int distance1 ;
                        if(min==4){
                            distance1 = LevenshteinDistance.computeLevenshteinDistance_Optimized(search.getBirthDay().substring(4), value.substring(0,min));
                            if (distance>distance1){
                                distance = distance1;
                            }
                        }
                        BigDecimal result = new BigDecimal(min - distance);
                        rate = result.divide(BigDecimal.valueOf(min), 2, RoundingMode.HALF_UP);
                        if(rate.compareTo(Constant.BRITHDAY_PP05)<0){
                            rate = BigDecimal.valueOf(-100);
                        }
                    }
                }
                //将匹配的生日日期返回
                //取id列表中最大匹配的值
                if (tarRate.compareTo(rate)<0||StringUtils.isEmpty(search.getBirthDay())){
                    tarRate = rate;
                    returnBir = value;
                }
            }
        }

        search.setBirthDay(returnBir);
        return this.isEnd(chainContext, tarRate);
    }

    @Override
    public String getDisplay() {
        return Constant.DS_BIRTHDAY;
    }
}
