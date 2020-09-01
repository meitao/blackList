package com.kingstar.bw.common;

import com.kingstar.bw.bean.Search;
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
 * @Description: ${description}
 * @Date: 20-8-26 上午11:39
 * @Version: 1.0
 */
@Service
public class AllBlackListEvent implements InitDataEvent {

    protected final Log logger = LogFactory.getLog(getClass());

    public static final String reg = ",";

    public static final String FILE_NAME = "/home/meitao/test/quan.csv";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onFire() {

        Map<String, Search> param = new HashMap<String, Search>(6000000);
        jdbcTemplate.setFetchSize(10000);


        String sql = new StringBuilder().append(" SELECT\n").append("  p.ID,  \n").append("  p.GENDER,  \n").append("  c.COUNTRY,  \n").append("  d.PEOPLE_DATE,  \n").append("  p.BIRTHPLACE \n").append("FROM\n").append("  AMLCONFIG.T_EXPOSED_PEOPLE_NEW p,\n").append("  AMLCONFIG.T_EXPOSED_PEOPLE_COUNTRY c,\n").append("  AMLCONFIG.T_EXPOSED_PEOPLE_DATE d\n").append("WHERE  p.ID = c.ID\n").append("  AND p.ID = d.ID\n").append("  AND c.COUNTRY_TYPE = 'Citizenship'\n").append("  AND d.DATE_TYPE = 'Date of Birth'").toString();


        long start = System.currentTimeMillis();
        jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                Search search = new Search();
                //如果字符串为空,则进行后续的处理
                if (StringUtils.isEmpty(rs.getString("ID"))) {
                    return "";
                } else {
                    search.setId(rs.getString("ID"));
                }

                if (!StringUtils.isEmpty(rs.getString("GENDER"))) {
                    search.setGender(rs.getString("GENDER"));
                }
                if (!StringUtils.isEmpty(rs.getString("COUNTRY"))) {
                    search.setNation(rs.getString("COUNTRY"));
                }

                if (!StringUtils.isEmpty(rs.getString("PEOPLE_DATE"))) {
                    search.setBirthDay(rs.getString("PEOPLE_DATE"));
                }

                if (!StringUtils.isEmpty(rs.getString("BIRTHPLACE"))) {
                    search.setAddr(rs.getString("BIRTHPLACE"));
                }

                param.put(rs.getString("ID"), search);
                return null;
            }
        });
        long end = System.currentTimeMillis();
        logger.info((end - start) + " name 加载成功!" + param.size());


//        Map<String, Search> param = new HashMap<String, Search>();
//
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
//                Search search = new Search();
//                String result = null;
//                for (int j = 0; j < words.length; j++) {
//                    result = null;
//                    result = words[j].replaceAll("\"", "");
//                    result = result.replaceAll("·", "");
//
//                    //如果字符串为空,则进行后续的处理
//                    if(StringUtils.isEmpty(result)){
//                        continue;
//                    }
//                    if (j == 0) {
//                        search.setId(result);
//                    }
//                    if (j == 1) {
//                        search.setName(result);
//                    }
//                    if (j == 2) {
//                        search.setNation(result);
//                    }
//                    if (j == 3) {
//                        search.setGender(result);
//                    }
//                    if (j == 4) {
//                        search.setBirthDay(result);
//                    }
//                    if (j == 6) {
//                        search.setNumber(result);
//                    }
//                    if (j == 7) {
//                        search.setAddr(result);
//                    }
//
//                }
//
//                if (StringUtils.isEmpty(search.getId())){
//                    logger.info(search);
//                    continue;
//                }
//                param.put(search.getId(), search);
//            }
//
        LocalData.setCollection(Constant.KEY_ALL, param);
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
