package com.kingstar.bw.common;

import com.kingstar.bw.event.InitDataListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-26 下午1:55
 * @Version: 1.0
 */
@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    InitDataListener initDataListener ;

    @Override
    public void run(String... args) throws Exception {
        //初始化数据
        initDataListener.init();
    }
}
