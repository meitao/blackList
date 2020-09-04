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

import java.sql.Connection;
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
        int batchSize = 100000;
        Map<String, List<Search>> param = new HashMap<String, List<Search>>(6000000);
        jdbcTemplate.setFetchSize(batchSize);
        //多线程处理匹配
        ExecutorService executorService =
                new ThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(1000000), new ThreadFactory() {
                    @Override
                    public Thread newThread(@NotNull Runnable r) {
                               return new Thread(r,"allBlackList"+System.nanoTime());
                    }
                });

//        String sql = new StringBuilder().append(" SELECT\n").append("  p.ID,  \n").append("  p.GENDER,  \n").append("  c.COUNTRY,  \n").append("  d.PEOPLE_DATE,  \n").append("  p.BIRTHPLACE \n").append("FROM\n").append("  AMLCONFIG.T_EXPOSED_PEOPLE_NEW p,\n").append("  AMLCONFIG.T_EXPOSED_PEOPLE_COUNTRY c,\n").append("  AMLCONFIG.T_EXPOSED_PEOPLE_DATE d\n").append("WHERE  p.ID = c.ID\n").append("  AND p.ID = d.ID\n").append("  AND c.COUNTRY_TYPE = 'Citizenship'\n").append("  AND d.DATE_TYPE = 'Date of Birth'").toString();
        String sql = "  SELECT * FROM ( SELECT\n" +
                "   rownum num,\n" +
                "      p.ID,\n" +
                "      p.BIRTHPLACE,\n" +
                "      n.NAME,\n" +
                "      i.ID_NO,\n" +
                "      p.GENDER,\n" +
                "      c.COUNTRY,\n" +
                "      d.PEOPLE_DATE\n" +
                "    FROM\n" +
                "     AMLCONFIG.T_EXPOSED_PEOPLE_NEW p\n" +
                "    LEFT JOIN  AMLCONFIG.T_EXPOSED_PEOPLE_NAME n ON\n" +
                "      p.ID = n.ID\n" +
                "    LEFT JOIN  AMLCONFIG.T_EXPOSED_PEOPLE_ID i ON\n" +
                "      p.ID = i.ID\n" +
                "    LEFT JOIN  AMLCONFIG.T_EXPOSED_PEOPLE_COUNTRY c ON\n" +
                "      p.ID = c.ID\n" +
                "    LEFT JOIN  AMLCONFIG.T_EXPOSED_PEOPLE_DATE d ON\n" +
                "      p.ID = d.ID\n" +
                "    WHERE\n" +
                "      c.COUNTRY_TYPE = 'Citizenship'\n" +
                "      AND d.DATE_TYPE = 'Date of Birth'\n" +
                "      AND rownum <=?)  WHERE num>? ";

        String countSql = "SELECT count(1) " +
                "    FROM\n" +
                "      AMLCONFIG.T_EXPOSED_PEOPLE_NEW p\n" +
                "    LEFT JOIN AMLCONFIG.T_EXPOSED_PEOPLE_NAME n ON\n" +
                "      p.ID = n.ID\n" +
                "    LEFT JOIN AMLCONFIG.T_EXPOSED_PEOPLE_ID i ON\n" +
                "      p.ID = i.ID\n" +
                "    LEFT JOIN AMLCONFIG.T_EXPOSED_PEOPLE_COUNTRY c ON\n" +
                "      p.ID = c.ID\n" +
                "    LEFT JOIN AMLCONFIG.T_EXPOSED_PEOPLE_DATE d ON\n" +
                "      p.ID = d.ID\n" +
                "    WHERE\n" +
                "      c.COUNTRY_TYPE = 'Citizenship'\n" +
                "      AND d.DATE_TYPE = 'Date of Birth'  ";
//                    "      AND d.DATE_TYPE = 'Date of Birth' and rownum <10000 ";
//            String sql ="SELECT\n" +
//                    "      p.ID,\n" +
//                    "      p.BIRTHPLACE,\n" +
//                    "      n.NAME,\n" +
//                    "      i.ID_NO,\n" +
//                    "      p.GENDER,\n" +
//                    "      c.COUNTRY,\n" +
//                    "      d.PEOPLE_DATE\n" +
//                    "    FROM\n" +
//                    "      T_EXPOSED_PEOPLE_NEW p\n" +
//                    "    LEFT JOIN T_EXPOSED_PEOPLE_NAME n ON\n" +
//                    "      p.ID = n.ID\n" +
//                    "    LEFT JOIN T_EXPOSED_PEOPLE_ID i ON\n" +
//                    "      p.ID = i.ID\n" +
//                    "    LEFT JOIN T_EXPOSED_PEOPLE_COUNTRY c ON\n" +
//                    "      p.ID = c.ID\n" +
//                    "    LEFT JOIN T_EXPOSED_PEOPLE_DATE d ON\n" +
//                    "      p.ID = d.ID\n" +
//                    "    WHERE\n" +
//                    "      c.COUNTRY_TYPE = 'Citizenship'\n" +
//                    "      AND d.DATE_TYPE = 'Date of Birth'  ";

        long start = System.currentTimeMillis();

//        String[] a = {"1000","10"};
//        jdbcTemplate.query(sql,a , new RowMapper<String>() {
//            @Override
//            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Search search = new Search();
//                String id = rs.getString("ID");
//                //如果字符串为空,则进行后续的处理
//                if (StringUtils.isEmpty(id)) {
//
//                    return null;
//                } else {
//                    search.setId(id);
//                }
//                String name = rs.getString("NAME");
//                if (!StringUtils.isEmpty(name)) {
//                    search.setName(name);
//                }
//                String number = rs.getString("ID_NO");
//                if (!StringUtils.isEmpty(number)) {
//                    search.setNumber(number);
//                }
//                String gender = rs.getString("GENDER");
//
//                if (!StringUtils.isEmpty(gender)) {
//                    search.setGender(gender);
//                }
//
//                String country = rs.getString("COUNTRY");
//                if (!StringUtils.isEmpty(country)) {
//                    search.setNation(country);
//                }
//
//                String birthDay = rs.getString("PEOPLE_DATE");
//                if (!StringUtils.isEmpty(birthDay)) {
//                    search.setBirthDay(birthDay);
//                }
//                String addr = rs.getString("PEOPLE_DATE");
//                if (!StringUtils.isEmpty(addr)) {
//                    search.setAddr(addr);
//                }
//                List<Search> searches = param.get(id);
//                if (searches == null) {
//                    searches = new ArrayList<Search>();
//                }
//                searches.add(search);
//                param.put(id, searches);
//
//                return null;
//            }
//        });
        //查询出总数
//        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);
        Integer count=500000;
        int row = 0;
        CountDownLatch countDownLatch = new CountDownLatch(count);
//        try {
////
////            Connection connection = jdbcTemplate.getDataSource().getConnection();
////            PreparedStatement preparedStatement = connection.prepareStatement(sql);
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }

        while (row < count) {
            int first = row;
            row = row + 500000;
            int finalRow = row;
            //最后一次跳出
            if(first<=count&&row>count){
                break;
            }
            logger.info("查询到行数"+finalRow);
            executorService.execute(()->{
                try {
                    logger.info(jdbcTemplate.getDataSource().getConnection());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                jdbcTemplate.query(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setInt(1, finalRow);
                        ps.setInt(2, first);
                    }
                }, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        logger.info(Thread.currentThread().getName()+" hang >"+rowNum);
                        Search search = new Search();
                        String id = rs.getString("ID");
                        //如果字符串为空,则进行后续的处理
                        if (StringUtils.isEmpty(id)) {
                            countDownLatch.countDown();
                            return null;
                        } else {
                            search.setId(id);
                        }
                        String name = rs.getString("NAME");
                        if (!StringUtils.isEmpty(name)) {
                            search.setName(name);
                        }
                        String number = rs.getString("ID_NO");
                        if (!StringUtils.isEmpty(number)) {
                            search.setNumber(number);
                        }
                        String gender = rs.getString("GENDER");

                        if (!StringUtils.isEmpty(gender)) {
                            search.setGender(gender);
                        }

                        String country = rs.getString("COUNTRY");
                        if (!StringUtils.isEmpty(country)) {
                            search.setNation(country);
                        }

                        String birthDay = rs.getString("PEOPLE_DATE");
                        if (!StringUtils.isEmpty(birthDay)) {
                            search.setBirthDay(birthDay);
                        }
                        String addr = rs.getString("BIRTHPLACE");
                        if (!StringUtils.isEmpty(addr)) {
                            search.setAddr(addr);
                        }
                        List<Search> searches = param.get(id);
                        if (searches == null) {
                            searches = new ArrayList<Search>();
                        }
                        searches.add(search);
                        param.put(id, searches);
                        countDownLatch.countDown();
                        return null;
                    }
                });
            });

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        jdbcTemplate.query(sql, new RowMapper<String>() {
//            @Override
//            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Search search = new Search();
//                String id = rs.getString("ID");
//                //如果字符串为空,则进行后续的处理
//                if (StringUtils.isEmpty(id)) {
//                    return null;
//                } else {
//                    search.setId(id);
//                }
//                String name = rs.getString("NAME");
//                if (!StringUtils.isEmpty(name)) {
//                    search.setName(name);
//                }
//                String number = rs.getString("ID_NO");
//                if (!StringUtils.isEmpty(number)) {
//                    search.setNumber(number);
//                }
//                String gender = rs.getString("GENDER");
//
//                if (!StringUtils.isEmpty(gender)) {
//                    search.setGender(gender);
//                }
//
//                String country = rs.getString("COUNTRY");
//                if (!StringUtils.isEmpty(country)) {
//                    search.setNation(country);
//                }
//
//                String birthDay = rs.getString("PEOPLE_DATE");
//                if (!StringUtils.isEmpty(birthDay)) {
//                    search.setBirthDay(birthDay);
//                }
//                String addr = rs.getString("PEOPLE_DATE");
//                if (!StringUtils.isEmpty(addr)) {
//                    search.setAddr(addr);
//                }
//                List<Search> searches = param.get(id);
//                if (searches == null) {
//                    searches = new ArrayList<Search>();
//                }
//                searches.add(search);
//                param.put(id, searches);
//                return null;
//            }
//        });
        executorService.shutdown();
        long end = System.currentTimeMillis();
        logger.info((end - start) + " allevent 加载成功!" + param.size());

        LocalData.setCollection(Constant.KEY_ALL, param);
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
//        LocalData.setCollection(Constant.KEY_ALL, param);
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
