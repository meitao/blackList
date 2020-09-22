package com.kingstar.bw.service.impl;

import com.kingstar.bw.bean.ChainContext;
import com.kingstar.bw.bean.Search;
import com.kingstar.bw.facade.MatchManagerFacade;
import com.kingstar.bw.service.MatchService;
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
import java.util.Date;
import java.util.List;

/**
 * 离线处理业务类
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
    public void match(double percision) {
        jdbcTemplate.setFetchSize(10000);
        count = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        String matchDate = formatter.format(date);
        logger.info("开始离线匹配!");
        //查询客户表数据
        jdbcTemplate.query("SELECT SCAN_LIST_TYPE, SCAN_ID_TYPE,CLIENT_ID,UNIQUE_ID, SCAN_NAME,SCAN_ID_NO,SCAN_GENDER,SCAN_NATIONALITY,SCAN_BIRTHDAY FROM  AMLDATA.V_NOR_CLIENT ", new RowMapper<String>() {
            //        jdbcTemplate.query("SELECT SCAN_LIST_TYPE, SCAN_ID_TYPE,CLIENT_ID,UNIQUE_ID, SCAN_NAME,SCAN_ID_NO,SCAN_GENDER,SCAN_NATIONALITY,SCAN_BIRTHDAY FROM  AMLDATA.V_NOR_CLIENT" +
//                " WHERE CLIENT_ID = '121901' AND  SCAN_ID_TYPE='0' AND SCAN_ID_NO='123456' AND SCAN_NAME='xing01' AND SCAN_LIST_TYPE='0'  AND UNIQUE_ID='20181219'  ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                count++;
                if ((count / 10000) == 0) {
                    logger.debug("处理第" + count + "条!");
                }
                String name = rs.getString("SCAN_NAME");
                String no = rs.getString("SCAN_ID_NO");
                String gender = rs.getString("SCAN_GENDER");
                String nation = rs.getString("SCAN_NATIONALITY");
                String birthday = rs.getString("SCAN_BIRTHDAY");
                String id = rs.getString("UNIQUE_ID");
                String listType = rs.getString("SCAN_LIST_TYPE");
                String idType = rs.getString("SCAN_ID_TYPE");
                String clientId = rs.getString("CLIENT_ID");
                if (StringUtils.isEmpty(name) && StringUtils.isEmpty(no)) {
                    return null;
                }
                Search search = new Search();
                search.setName(name);
                search.setNumber(no);
                search.setGender(gender);
                search.setBirthDay(birthday);
                search.setNation(nation);
                search.setPercision(percision);
                ChainContext chainContext = new ChainContext();
                chainContext.setSearch(search);
                try {
                    List<ChainContext> list = matchManagerFacade.match(chainContext);
                    //如果有匹配结果，存到库中
                    if (!list.isEmpty()) {
                        for (ChainContext cc : list) {
                            Search search1 = cc.getSearch();
                            String[] obj = new String[9];
                            obj[0] = matchDate;
                            obj[1] = id;
                            obj[2] = cc.getSumRate().toString();
                            obj[3] = listType;
                            obj[4] = name;
                            obj[5] = no;
                            obj[6] = idType;
                            obj[7] = clientId;
                            obj[8] = search1.getId();
                            if (logger.isDebugEnabled()) {
                                for (int j = 0; j < obj.length; j++) {
                                    logger.info(obj[j]);
                                }
                            }
                            jdbcTemplate.update("INSERT INTO AMLDATA.T_MATCH_BLACKLIST (MATCH_DATE, ID, RATE, SCAN_LIST_TYPE, SCAN_NAME, SCAN_ID_NO, SCAN_ID_TYPE, CLIENT_ID, MATCH_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?,?)", obj);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
                return null;
            }
        });
        logger.info("离线匹配完成!");
    }
}
