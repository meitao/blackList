package com.kingstar.bw;

import com.kingstar.bw.common.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlackListApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlackListApplication.class, args);
    }

    @Bean
    public InitDataListener initDataListener() {
        InitDataListener initDataListener =  new DefaultInitDataListenerImpl();
        initDataListener.add(new NameListEvent());
        initDataListener.add(new NumberListEvent());
        initDataListener.add(new AllBlackListEvent());
        initDataListener.add(new AddrVecEvent());
        return initDataListener;
    }
}
