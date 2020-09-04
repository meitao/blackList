package com.kingstar.bw.common;

import com.kingstar.bw.bean.Search;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @Author: meitao
 * @Description:
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
@Service
public class MainBlackListEvent implements InitDataEvent {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onFire() {

        int batchSize = 100000;
        Map<String, List<Search>> param = new HashMap<String, List<Search>>(6000000);
        jdbcTemplate.setFetchSize(batchSize);
        long start = System.currentTimeMillis();
        /*
         * 根据名字的字符串长度进行分片，不同的长度在不同的分片上，分片的主节点在list上，长度为list的下标
         * @todo在根据个人和机构分成两个list
         *
         */
        jdbcTemplate.query("SELECT  id,GENDER, BIRTHPLACE FROM  AMLCONFIG.T_EXPOSED_PEOPLE_NEW c  ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                Search search = new Search();
                String id = rs.getString("ID");
                //如果字符串为空,则进行后续的处理
                if (StringUtils.isEmpty(id)) {
                    return null;
                } else {
                    search.setId(id);
                }

                String gender = rs.getString("GENDER");

                if (!StringUtils.isEmpty(gender)) {
                    search.setGender(gender);
                }

                String addr = rs.getString("BIRTHPLACE");
                if (!StringUtils.isEmpty(addr)) {
                    search.setAddr(addr);
                }
                List<Search> searches = param.get(id);
                if (searches == null) {
                    searches = new ArrayList<Search>();
                }
                //新增或更新name对应的值
                searches.add(search);
                param.put(id, searches);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end - start) + " main 加载成功!" + param.size());
        LocalData.setCollection(Constant.KEY_ALL, param);
    }
}
