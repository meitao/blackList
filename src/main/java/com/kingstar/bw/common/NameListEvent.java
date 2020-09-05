package com.kingstar.bw.common;

import com.kingstar.bw.exception.PlatException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: meitao
 * @Description: 名称数据的
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
@Service
public class NameListEvent implements InitDataEvent {

//    public static final String reg = ",";

    protected final Log logger = LogFactory.getLog(getClass());


    @Autowired
    private JdbcTemplate jdbcTemplate;


//    public static final String FILE_NAME = "/home/meitao/test/xm.csv";


    @Override
    public void onFire() {

        Map<String, Map<String, List<String>>> map = new HashMap<String, Map<String, List<String>>>(100000);
        Map<String, List<String>> mapId = new HashMap<String, List<String>>(1000000);
        jdbcTemplate.setFetchSize(Constant.INIT_FETCH_SIZE);

        long start = System.currentTimeMillis();

        /*
         * 根据名字的字符串长度进行分片，不同的长度在不同的分片上，分片的主节点在list上，长度为list的下标
         * @todo在根据个人和机构分成两个list
         *
         */
        jdbcTemplate.query("SELECT  id, NAME FROM AMLCONFIG.T_EXPOSED_PEOPLE_NAME ", new RowMapper<String>() {
//        jdbcTemplate.query("SELECT  id, NAME FROM T_EXPOSED_PEOPLE_NAME ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString("NAME");
                String id = rs.getString("ID");
                if (StringUtils.isEmpty(name)) {
                    return null ;
                }
                CommondUtil.putPatition(name,id,map);
                //以id为key值
                CommondUtil.storeMap(id,name,mapId);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end - start) + "  加载成功!" + map.size());
        LocalData.setCollection(Constant.KEY_NAME, map);
        LocalData.setCollection(Constant.KEY_NAME_ID, mapId);

//        List<String> taaccountids = jdbcTemplate.query("select sysdate from dual", new RowMapper<String>() {
//            @Override
//            public String mapRow(ResultSet resultSet, int i) throws SQLException {
//                return resultSet.getString("sysdate");
//            }
//        });


//        BufferedReader bufferedReader = null;
//        try {
//            bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
//            String line;
//            int i = 0;
//            while ((line = bufferedReader.readLine()) != null) {
//                //跳过第一行
//                i++;
//                if (i == 1) {
//                    continue;
//                }
//                //将数据以逗号分割
//                String[] words = line.split(reg);
//                String name = words[1].replaceAll("\"", "");
//                name = name.replaceAll("·", "");
//                param.put(words[0], name);
//            }
//

//        } catch (IOException e) {
//            throw new PlatException(e);
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    throw new PlatException(e);
//                }
//            }
//        }
    }
}
