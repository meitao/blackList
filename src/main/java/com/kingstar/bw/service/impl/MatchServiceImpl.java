package com.kingstar.bw.service.impl;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.CommondUtil;
import com.kingstar.bw.facade.MatchManagerFacade;
import com.kingstar.bw.service.MatchService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchManagerFacade matchManagerFacade;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected final Log logger = LogFactory.getLog(getClass());

    int count = 0;

    @Override
    public void match() {
        jdbcTemplate.setFetchSize(1000);
        count = 0;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        String matchDate=formatter.format(date);

        //查询客户表数据
        jdbcTemplate.query("SELECT UNIQUE_ID, SCAN_NAME，SCAN_ID_NO，SCAN_GENDER，SCAN_NATIONALITY，SCAN_BIRTHDAY FROM  AMLDATA.V_NOR_CLIENT", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                if((count/10000)==0){
                    logger.debug("处理"+count++);
                }

                String name = rs.getString("SCAN_NAME");
                String no = rs.getString("SCAN_ID_NO");
                String gender = rs.getString("SCAN_GENDER");
                String nation = rs.getString("SCAN_NATIONALITY");
                String birthday = rs.getString("SCAN_BIRTHDAY");
                String id = rs.getString("UNIQUE_ID");
                if (StringUtils.isEmpty(name)&&StringUtils.isEmpty(no)) {
                    return null ;
                }
                Search search = new Search();
                search.setName(name);
                search.setNumber(no);
                search.setGender(gender);
                search.setBirthDay(birthday);
                search.setNation(nation);
                ChainContext chainContext = new ChainContext();
                chainContext.setSearch(search);

                List<ChainContext> list = matchManagerFacade.match(chainContext);
                //如果有匹配结果，存到库中
                if (!list.isEmpty()){
                    for (ChainContext cc:list){
                        Search search1 = cc.getSearch();
                        String[] obj = new String[3];
                        obj[0] = matchDate;
                        obj[1] = id;
                        obj[2] = cc.getSumRate().toString();
                        logger.info("id:"+obj[1]+" rate:"+ obj[2]);
                        jdbcTemplate.update("INSERT INTO AMLDATA.T_MATCH_BLACKLIST (MATCH_DATE, ID, RATE) VALUES(?, ?, ?) ");
                    }
                }
                return null;
            }
        });
    }
}
