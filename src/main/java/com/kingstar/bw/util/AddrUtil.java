package com.kingstar.bw.util;

/**
 * @ClassName AddrUtil
 * @Description
 * @Author tao.mei
 * @Date 2020/10/10 9:32
 * @modify
 * @Review
 */
public class AddrUtil {
    public static final String[] UNUSEFUL_WORDS ={"group","village","subdistrict","county","township","st.","rd.","town","district","city","avenue","community","zone","neighborhood","No.","street","division","road","unit","room","/f","rd"};

    public static String unuseWord(String address){
        //去除无用词
        for (String word :UNUSEFUL_WORDS){
            address =  address.replaceAll(word,"");
        }
        return address;
    }

}
