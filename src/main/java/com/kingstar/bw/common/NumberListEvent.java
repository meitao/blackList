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
 * @Description: ${description}
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
@Service
public class NumberListEvent implements InitDataEvent {

    public static final String reg = ",";

    public static final String FILE_NAME = "/home/meitao/test/zhengjian.csv";

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void onFire() {
        Map<String, Map<String, List<String>>> map = new HashMap<String, Map<String, List<String>>>(600);
        jdbcTemplate.setFetchSize(100000);
        long start = System.currentTimeMillis();
        jdbcTemplate.query(" SELECT  id,ID_NO FROM AMLCONFIG.T_EXPOSED_PEOPLE_ID ", new RowMapper<String>() {
//        jdbcTemplate.query(" SELECT  id,ID_NO FROM  T_EXPOSED_PEOPLE_ID ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String name = rs.getString("ID_NO");
                    String id = rs.getString("ID");
                    //判断名字的长度获取相应的hash
                    if (StringUtils.isEmpty(name)) {
                        return null;
                    }
                CommondUtil.putPatition(name,id,map);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end-start)+" number 加载成功!"+map.size());
        LocalData.setCollection(Constant.KEY_NUMBER,map);

//        Map<String, String> param = new HashMap<String, String>();
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
//                String name =  words[1].replaceAll("·", "");
//                param.put(words[0], name);
//            }
//            LocalData.setCollection(Constant.KEY_NUMBER,param);
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
