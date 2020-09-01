package com.kingstar.bw.filter;

import com.kingstar.bw.common.Constant;
import org.apache.commons.chain.impl.ChainBase;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-27 下午5:10
 * @Version: 1.0
 */
public class OrgMatchChain extends ChainBase {

    //机构,证件号,国家,地址 处理链
    public void init() {

        NationMatchCommond nationMatchCommond = new NationMatchCommond();
        Params params3 = new Params();
        params3.setRate(Constant.PERCISION);
        params3.setWeight(Constant.NATION_WEIGHT);
        nationMatchCommond.setParams(params3);
        addCommand(nationMatchCommond);


        AddrMatchCommond addrMatchCommond = new AddrMatchCommond();
        Params params4 = new Params();
        params4.setRate(Constant.PERCISION);
        params4.setWeight(Constant.ADDR_WEIGHT);
        addrMatchCommond.setParams(params4);
        addCommand(addrMatchCommond);

    }
}