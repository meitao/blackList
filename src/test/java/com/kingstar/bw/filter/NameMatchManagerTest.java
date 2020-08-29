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

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-28 下午4:11
 * @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NameMatchManagerTest {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    NameMatchManager matchManager;

    @Test
    public void match() {
        ChainContext chainContext = new ChainContext();
        Search search = new Search();
        search.setPer(true);
        search.setId("11185858");
        search.setName("皮長明");
        search.setBirthDay("2018");
        search.setAddr("CHINA Chongqing Fenglin Road");
        chainContext.setSearch(search);
        long start = System.currentTimeMillis();
//        matchManager.setRate(new BigDecimal("0.5"));
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
