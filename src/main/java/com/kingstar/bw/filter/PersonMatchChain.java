package com.kingstar.bw.filter;

import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-27 下午5:10
 * @Version: 1.0
 */
public class PersonMatchChain extends ChainBase {

    //个人按照证件号,姓别(排除项),国家(排除项),出生日期(排除项),地址,处理链
    public void init(Search search) {

        //只对非空项进行判断
        if (!StringUtils.isEmpty(search.getGender())) {
            GenderMatchCommond genderMatchCommond = new GenderMatchCommond();
            Params params2 = new Params();
//            params2.setRate(Constant.PERCISION);
            params2.setWeight(Constant.GENDER_WEIGHT);
            genderMatchCommond.setParams(params2);
            addCommand(genderMatchCommond);
        }

        if (!StringUtils.isEmpty(search.getNation())) {
            NationMatchCommond nationMatchCommond = new NationMatchCommond();
            Params params3 = new Params();
//            params3.setRate(Constant.PERCISION);
            params3.setWeight(Constant.NATION_WEIGHT);
            nationMatchCommond.setParams(params3);
            addCommand(nationMatchCommond);
        }

        if (!StringUtils.isEmpty(search.getBirthDay())) {
            BrithDayMatchCommond brithDayMatchCommond = new BrithDayMatchCommond();
            Params params1 = new Params();
//            params1.setRate(Constant.PERCISION);
            params1.setWeight(Constant.BRITHDAY_WEIGHT);
            brithDayMatchCommond.setParams(params1);
            addCommand(brithDayMatchCommond);

        }

        if (!StringUtils.isEmpty(search.getAddr())) {
            AddrMatchCommond addrMatchCommond = new AddrMatchCommond();
            Params params4 = new Params();
//            params4.setRate(Constant.PERCISION);
            params4.setWeight(Constant.ADDR_WEIGHT);
            addrMatchCommond.setParams(params4);
            addCommand(addrMatchCommond);
        }
    }
}
