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
 * @Description: 加载证件号数据
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
@Service
public class NumberListEvent implements InitDataEvent {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void onFire() {
        Map<String, Map<String, List<String>>> map = new HashMap<String, Map<String, List<String>>>(100000);
        Map<String, List<String>> mapId = new HashMap<String, List<String>>(100000);

        jdbcTemplate.setFetchSize(Constant.INIT_FETCH_SIZE);
        long start = System.currentTimeMillis();
        jdbcTemplate.query(" SELECT  id,ID_NO FROM AMLCONFIG.T_EXPOSED_PEOPLE_ID ", new RowMapper<String>() {
//        jdbcTemplate.query(" SELECT  id,ID_NO FROM  T_EXPOSED_PEOPLE_ID ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String idNo = rs.getString("ID_NO");
                    String id = rs.getString("ID");
                    //判断名字的长度获取相应的hash
                    if (StringUtils.isEmpty(idNo)) {
                        return null;
                    }
                CommondUtil.putPatition(idNo,id,map);
                //以id为key值
                CommondUtil.storeMap(id,idNo,mapId);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end-start)+" number 加载成功!"+map.size());
        LocalData.setCollection(Constant.KEY_NUMBER,map);
        LocalData.setCollection(Constant.KEY_NUMBER_ID,mapId);

//        Map<String, String> param = new HashMap<String, String>();
//        BufferedReader bufferedReader = null;
//        try {
//            bufferedReader = new BufferedReader(new FileReader(FILE_DIR));
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
