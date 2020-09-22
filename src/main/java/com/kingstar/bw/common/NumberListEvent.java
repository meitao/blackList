package com.kingstar.bw.common;

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
        Map<String, Map<String, List<String>>> mapPer = new HashMap<String, Map<String, List<String>>>(1000);
        Map<String, Map<String, List<String>>> mapEntity = new HashMap<String, Map<String, List<String>>>(1000);
        Map<String, List<String>> mapId = new HashMap<String, List<String>>(100000);

        jdbcTemplate.setFetchSize(Constant.INIT_FETCH_SIZE);
        long start = System.currentTimeMillis();
        //加载个人的证件数据
        jdbcTemplate.query(" SELECT  id,ID_NO FROM AMLCONFIG.T_EXPOSED_PEOPLE_ID  WHERE ENTITY_TYPE='Person'  ", new RowMapper<String>() {
            //        jdbcTemplate.query(" SELECT  id,ID_NO FROM  T_EXPOSED_PEOPLE_ID ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                initData(  rs,  mapPer,  mapId);
                return null;
            }
        });
        //加载机构的证件数据
        jdbcTemplate.query(" SELECT  id,ID_NO FROM AMLCONFIG.T_EXPOSED_PEOPLE_ID   WHERE ENTITY_TYPE='Entity'", new RowMapper<String>() {
            //        jdbcTemplate.query(" SELECT  id,ID_NO FROM  T_EXPOSED_PEOPLE_ID ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                initData(  rs,  mapEntity,  mapId);
                return null;
            }
        });

        long end = System.currentTimeMillis();
        logger.info((end-start)+" number 加载成功!"+mapPer.size());
        LocalData.setCollection(Constant.KEY_NUMBER_PER,mapPer);
        LocalData.setCollection(Constant.KEY_NUMBER_ENTITY,mapEntity);
        LocalData.setCollection(Constant.KEY_NUMBER_ID,mapId);
    }
    /**
     * 初始化参数
     * @param rs
     * @param param
     */
    private void initData(ResultSet rs,Map param,Map mapId) throws SQLException {
        String idNo = rs.getString("ID_NO");
        String id = rs.getString("ID");
        //判断名字的长度获取相应的hash
        if (StringUtils.isEmpty(idNo)) {
            return  ;
        }
        CommondUtil.putPatition(idNo,id,param);
        //以id为key值
        CommondUtil.storeMap(id,idNo,mapId);
    }
}
