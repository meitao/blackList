package com.kingstar.bw;

import com.kingstar.bw.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class BlackListApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NameListEvent nameListEvent ;

    @Autowired
    private NumberListEvent numberListEvent ;

    @Autowired
    private AllBlackListEvent allBlackListEvent ;
    @Autowired
    private AddrVecEvent addrVecEvent ;




    public static void main(String[] args) {
        SpringApplication.run(BlackListApplication.class, args);
    }

    @Bean
    public InitDataListener initDataListener() {

        InitDataListener initDataListener =  new DefaultInitDataListenerImpl();
        initDataListener.add(nameListEvent);
        initDataListener.add(numberListEvent);
        initDataListener.add(allBlackListEvent);
        initDataListener.add(addrVecEvent);
        return initDataListener;
    }
}
