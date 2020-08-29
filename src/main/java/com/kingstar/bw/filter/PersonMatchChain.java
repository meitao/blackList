package com.kingstar.bw.filter;

import org.apache.commons.chain.impl.ChainBase;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-27 下午5:10
 * @Version: 1.0
 */
public class PersonMatchChain extends ChainBase {

    //个人按照证件号,姓别(排除项),国家(排除项),出生日期(排除项),地址,处理链
    public void init() {
        String rate="0.5";


        GenderMatchCommond genderMatchCommond = new GenderMatchCommond();
        Params params2 = new Params();
        params2.setRate(new BigDecimal(rate));
        params2.setWeight(new BigDecimal("0.05"));
        genderMatchCommond.setParams(params2);
        addCommand(genderMatchCommond);

        NationMatchCommond nationMatchCommond = new NationMatchCommond();
        Params params3 = new Params();
        params3.setRate(new BigDecimal(rate));
        params3.setWeight(new BigDecimal("0.05"));
        nationMatchCommond.setParams(params3);
        addCommand(nationMatchCommond);

        BrithDayMatchCommond brithDayMatchCommond = new BrithDayMatchCommond();
        Params params1 = new Params();
        params1.setRate(new BigDecimal(rate));
        params1.setWeight(new BigDecimal("0.05"));
        brithDayMatchCommond.setParams(params1);
        addCommand(brithDayMatchCommond);


        AddrMatchCommond addrMatchCommond = new AddrMatchCommond();
        Params params4 = new Params();
        params4.setRate(new BigDecimal(rate));
        params4.setWeight(new BigDecimal("0.05"));
        addrMatchCommond.setParams(params4);
        addCommand(addrMatchCommond);

    }
}
