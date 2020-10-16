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
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


//    public static final String FILE_DIR = "/home/meitao/test/xm.csv";


    @Override
    public void onFire() {

        Map<String, Map<String, List<String>>> mapEntity = new HashMap<String, Map<String, List<String>>>(1000);
        Map<String, Map<String, List<String>>> mapPer = new HashMap<String, Map<String, List<String>>>(1000);
//        Map<String, List<String>> mapId = new HashMap<String, List<String>>(1000000);
        jdbcTemplate.setFetchSize(Constant.INIT_FETCH_SIZE);

        long start = System.currentTimeMillis();

        /*
         * 根据名字的字符串长度进行分片，不同的长度在不同的分片上，分片的主节点在list上，长度为list的下标
         * @todo在根据个人和机构分成两个list
         *
         */
        jdbcTemplate.query("SELECT  id, NAME FROM AMLCONFIG.T_EXPOSED_PEOPLE_NAME  WHERE ENTITY_TYPE='Person' ", new RowMapper<String>() {
            //        jdbcTemplate.query("SELECT  id, NAME FROM T_EXPOSED_PEOPLE_NAME ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                initData(rs, mapPer);
                //以id为key值
//                CommondUtil.storeMap(id,name,mapId);
                return null;
            }
        });

        jdbcTemplate.query("SELECT  id, NAME FROM AMLCONFIG.T_EXPOSED_PEOPLE_NAME  WHERE ENTITY_TYPE='Entity' ", new RowMapper<String>() {
            //        jdbcTemplate.query("SELECT  id, NAME FROM T_EXPOSED_PEOPLE_NAME ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                initData(rs, mapEntity);
                //以id为key值
//                CommondUtil.storeMap(id,name,mapId);
                return null;
            }
        });


        long end = System.currentTimeMillis();
        //记录不同长度的名称数量
        Set<Map.Entry<String, Map<String, List<String>>>> set = mapPer.entrySet();
        for (Map.Entry<String, Map<String, List<String>>> entry : set) {
            logger.info("长度为"+entry.getKey()+"的个人名称数量:" + entry.getValue().size());
        }

        Set<Map.Entry<String, Map<String, List<String>>>> setEntity = mapEntity.entrySet();
        for (Map.Entry<String, Map<String, List<String>>> entry : setEntity) {
            logger.info("长度为"+entry.getKey()+"的机构名称数量:" + entry.getValue().size());
        }

        logger.info((end - start) + " name  加载成功!" + mapPer.size());
        LocalData.setCollection(Constant.KEY_NAME_PER, mapPer);
        LocalData.setCollection(Constant.KEY_NAME_ENTITY, mapEntity);
//        LocalData.setCollection(Constant.KEY_NAME_ID, mapId);

    }

    /**
     * 初始化参数
     *
     * @param rs
     * @param param
     */
    private void initData(ResultSet rs, Map param) throws SQLException {
        String name = rs.getString("NAME");
        String id = rs.getString("ID");
        if (StringUtils.isEmpty(name)) {
            return;
        }
        CommondUtil.putPatition(name, id, param);
    }
}
