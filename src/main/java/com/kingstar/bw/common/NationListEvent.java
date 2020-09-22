package com.kingstar.bw.common;

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
        jdbcTemplate.setFetchSize(Constant.INIT_FETCH_SIZE);
        long start = System.currentTimeMillis();
        /*
         * 根据名字的字符串长度进行分片，不同的长度在不同的分片上，分片的主节点在list上，长度为list的下标
         * @todo在根据个人和机构分成两个list
         *
         */
        jdbcTemplate.query("SELECT  id, COUNTRY FROM  AMLCONFIG.T_EXPOSED_PEOPLE_COUNTRY c where c.COUNTRY_TYPE = 'Citizenship' ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String nation = rs.getString("COUNTRY");
                String id = rs.getString("ID");
                CommondUtil.storeMap(id,nation,map);

                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end - start) + " nation 加载成功!" + map.size());
        LocalData.setCollection(Constant.KEY_NATION, map);
    }
}
