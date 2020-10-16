package com.kingstar.bw.event;

import com.kingstar.bw.bean.Search;
import com.kingstar.bw.common.Constant;
import com.kingstar.bw.common.LocalData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: meitao
 * @Description: 性别和地址数据
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

        Map<String, Search> param = new HashMap<String,Search>(6000000);
        jdbcTemplate.setFetchSize(Constant.INIT_FETCH_SIZE);
        long start = System.currentTimeMillis();
        /*
         * 根据名字的字符串长度进行分片，不同的长度在不同的分片上，分片的主节点在list上，长度为list的下标
         * @todo在根据个人和机构分成两个list
         *
         */
        jdbcTemplate.query("SELECT  id,GENDER FROM  AMLCONFIG.T_EXPOSED_PEOPLE_NEW c  ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                Search search = new Search();
                String id = rs.getString("ID");
                //如果字符串为空,则进行后续的处理，该表中id唯一
                if (StringUtils.isEmpty(id)) {
                    return null;
                } else {
                    search.setId(id);
                }

                String gender = rs.getString("GENDER");

                if (!StringUtils.isEmpty(gender)) {
                    search.setGender(gender);
                }

//                String addr = rs.getString("BIRTHPLACE");
//                if (!StringUtils.isEmpty(addr)) {
//                    search.setAddr(addr);
//                }
                //新增或更新name对应的值
                param.put(id, search);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end - start) + " main 加载成功!" + param.size());
        LocalData.setCollection(Constant.KEY_ALL, param);
    }
}
