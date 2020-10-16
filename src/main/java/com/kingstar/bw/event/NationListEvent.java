package com.kingstar.bw.event;

import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import com.kingstar.bw.util.CommondUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: meitao
 * @Description: 加载国家数据
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
@Service
public class NationListEvent implements InitDataEvent {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onFire() {

        Map<String, List<String>> map = new HashMap<String, List<String>>(3000000);

        Map<String,String> shortMap = new HashMap<String,String>(800);
        jdbcTemplate.setFetchSize(Constant.INIT_FETCH_SIZE);
        long start = System.currentTimeMillis();

        jdbcTemplate.query("SELECT  id, COUNTRY FROM  AMLCONFIG.T_EXPOSED_PEOPLE_COUNTRY c where c.COUNTRY_TYPE = 'Citizenship' ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String nation = rs.getString("COUNTRY");
                String id = rs.getString("ID");
                CommondUtil.storeMap(id,nation,map);

                return null;
            }
        });
        //加载国家简称对应表
        jdbcTemplate.query("SELECT EN_NAME, CN_NAME, SHORT_NAME FROM AMLDATA.T_COUNTRY_NAME ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String enName = rs.getString("EN_NAME").toUpperCase();
                String cnName = rs.getString("CN_NAME");
                String shortName = rs.getString("SHORT_NAME").toUpperCase();
                shortMap.put(enName,shortName);
                shortMap.put(cnName,shortName);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end - start) + " nation 加载成功!" + map.size());
        LocalData.setCollection(Constant.KEY_NATION, map);
        LocalData.setCollection(Constant.KEY_SORT_NATION, shortMap);
    }
}
