package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-28 上午10:54
 * @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrgMatchChainTest {

    protected final Log logger = LogFactory.getLog(getClass());
    static OrgMatchChain orgMatchChain;

    @BeforeClass
    public static void init() {
        orgMatchChain = new OrgMatchChain();
        String rate = "0.5";

        NameMatchCommond nameMatchCommond = new NameMatchCommond();
        Params params = new Params();
        params.setRate(new BigDecimal(rate));
        params.setWeight(new BigDecimal("0.5"));
        nameMatchCommond.setParams(params);
        orgMatchChain.addCommand(nameMatchCommond);


        NumberMatchCommond numberMatchCommond = new NumberMatchCommond();
        Params params1 = new Params();
        params1.setRate(new BigDecimal(rate));
        params1.setWeight(new BigDecimal("0.5"));
        numberMatchCommond.setParams(params1);
        orgMatchChain.addCommand(numberMatchCommond);


        GenderMatchCommond genderMatchCommond = new GenderMatchCommond();
        Params params2 = new Params();
        params2.setRate(new BigDecimal(rate));
        params2.setWeight(new BigDecimal("0.05"));
        genderMatchCommond.setParams(params2);
        orgMatchChain.addCommand(genderMatchCommond);

        BrithDayMatchCommond brithDayMatchCommond = new BrithDayMatchCommond();
        Params params3 = new Params();
        params3.setRate(new BigDecimal(rate));
        params3.setWeight(new BigDecimal("0.05"));
        brithDayMatchCommond.setParams(params3);
        orgMatchChain.addCommand(brithDayMatchCommond);


        AddrMatchCommond addrMatchCommond = new AddrMatchCommond();
        Params params4 = new Params();
        params4.setRate(new BigDecimal(rate));
        params4.setWeight(new BigDecimal("0.05"));
        addrMatchCommond.setParams(params4);
        orgMatchChain.addCommand(addrMatchCommond);


    }

    @Test
    public void test1() {
        ChainContext chainContext = new ChainContext();
        try {
            Search search = new Search();
            search.setPer(true);
            search.setId("11185858");
            search.setName("皮長明");
            search.setNumber("1231");
            search.setBirthDay("2018");
            search.setAddr("CHINA Chongqing Fenglin Road");

            chainContext.setSearch(search);

            orgMatchChain.execute(chainContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
