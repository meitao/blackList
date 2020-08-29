package com.kingstar.bw.filter;

import com.kingstar.bw.bean.Search;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 下午3:58
 * @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NameMatchTest {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    NameMatch  nameMatch ;
    @Test
    public void match() {
//        nameMatch.setMatchRate(new BigDecimal(0.5));
//        long start = System.currentTimeMillis();
//        Search search = new Search();
//        search.setName("沈國雄");
//        List list = nameMatch.match(search);
//        long end = System.currentTimeMillis();
//        logger.info("----match---大小-->"+list.size());
//        logger.info(end-start);
//        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void notMatch() {
//        nameMatch.setMatchRate(new BigDecimal(0.8));
//        long start = System.currentTimeMillis();
//        Search search = new Search();
//        search.setName("张三");
//        List list = nameMatch.match(search);;
//        long end = System.currentTimeMillis();
//        logger.info("----match---大小-->"+list.size());
//        logger.info(end-start);
//        Assert.assertTrue(list.isEmpty());
    }
}
