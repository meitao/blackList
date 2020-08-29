package com.kingstar.bw.filter;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-29 下午12:51
 * @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NumberMatchManagerTest {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    NumberMatchManager matchManager;

    @Test
    public void matchNameNum() {
        ChainContext chainContext = new ChainContext();
        Search search = new Search();
        search.setPer(true);
        search.setId("11185858");
        search.setNumber("133028196310142211");
        search.setName("苏志凡");
        search.setBirthDay("2018");
//        search.setGender("0");
        search.setAddr("CHINA Chongqing Fenglin Road");
        chainContext.setSearch(search);
        long start = System.currentTimeMillis();

        List<ChainContext> list = matchManager.match(chainContext);

        long end = System.currentTimeMillis();
        logger.info("耗时:" + (end - start));
        for (ChainContext c : list) {
            logger.info("name:" + c.getSearch().getName());
            logger.info("getNation:" + c.getSearch().getNation());
            logger.info("getBirthDay:" + c.getSearch().getBirthDay());
            logger.info("getNumber:" + c.getSearch().getNumber());
            logger.info("getAddr:" + c.getSearch().getAddr());
            logger.info("rate:" + c.getSumRate());
        }

    }

    @Test
    public void matchNum() {
        ChainContext chainContext = new ChainContext();
        Search search = new Search();
        search.setPer(true);
        search.setId("11185858");
        search.setNumber("133028196310142211");
        search.setGender("0");
        search.setBirthDay("2018");
        search.setAddr("CHINA Chongqing Fenglin Road");
        chainContext.setSearch(search);
        long start = System.currentTimeMillis();

        List<ChainContext> list = matchManager.match(chainContext);

        long end = System.currentTimeMillis();
        logger.info("耗时:" + (end - start));
        for (ChainContext c : list) {
            logger.info("name:" + c.getSearch().getName());
            logger.info("getNation:" + c.getSearch().getNation());
            logger.info("getBirthDay:" + c.getSearch().getBirthDay());
            logger.info("getNumber:" + c.getSearch().getNumber());
            logger.info("getAddr:" + c.getSearch().getAddr());
            logger.info("rate:" + c.getSumRate());
        }

    }
}