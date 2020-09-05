package com.kingstar.bw.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: meitao
 * @Description: 生日数据
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
@Service
public class BirthdayListEvent implements InitDataEvent {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onFire() {

        Map<String, List<String>> map = new HashMap<String, List<String>>(100000);
        jdbcTemplate.setFetchSize(Constant.INIT_FETCH_SIZE);
        long start = System.currentTimeMillis();
        /*
         * 根据名字的字符串长度进行分片，不同的长度在不同的分片上，分片的主节点在list上，长度为list的下标
         * @todo在根据个人和机构分成两个list
         *
         */
        jdbcTemplate.query("SELECT  id, PEOPLE_DATE FROM  AMLCONFIG.T_EXPOSED_PEOPLE_DATE c  ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String birthday = rs.getString("PEOPLE_DATE");
                String id = rs.getString("ID");

                CommondUtil.storeMap(id,birthday,map);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end - start) + " name 加载成功!" + map.size());
        LocalData.setCollection(Constant.KEY_BIRTHDAY, map);
    }
}
