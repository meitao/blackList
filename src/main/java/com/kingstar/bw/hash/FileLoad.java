package com.kingstar.bw.hash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileLoad {
    Map<String,Map> param = new HashMap<String,Map> ();

    public static final String reg =",";

    public void load(String fileName){

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line ;
            int i = 0;

            //map
//            MutilMap param = new MutilMap ();

            while((line =bufferedReader.readLine())!=null){
                //跳过第一行
                i++;
                if (i==1){
                    continue;
                }

                //将数据以逗号分割
                String[]  words =  line.split(reg);

//               String id = words[0].replaceAll("\"","");
               String name = words[1].replaceAll("\"","");
               if("買合木提·吾斯曼".equals(name)){
                   System.out.println("");
               }
                  name = name.replaceAll("·","");
                //将名字拆解
                char[] chars =  name.toCharArray();
                Map<String,Map> paramTemp = null ;
                for (int k=0;k<chars.length;k++) {
                   String key = String.valueOf(chars[k]);
//                   当是第一个字符时要在顶层的map中去找
                   if(k==0){
                       paramTemp = param;
                   }
                   //如果找到对应的map值获取下一个map
                  if(paramTemp.containsKey(key)){
                      paramTemp = paramTemp.get(key);
                  }else{
                      //找不到对应的字符,创建下一层map
                       Map<String,Map> newPara = new HashMap<String,Map> ();
                      //hash
                      paramTemp.put(key,newPara);
                      //将一层map传给临时map
                      paramTemp = newPara;
                  }

                   //当获取map中的下一级


                    //当字符在字符窜中的排序为对应层级,判断是否在该层级中是否有值,有就跳过,没有则插入
//                    if(k==param.getLevel()){
//                        if(!param.getPara().containsKey(chars[k])){
//                            MutilMap mutilMap = new MutilMap();
//                            mutilMap.setLevel(i+1);
//                            param.getPara().put(key, mutilMap);
//                        }
//                    }


                }
                //将名字,拼音的信息存入到map中



                //根据名字字数去构建map的层级

            }
            System.out.println(param.size());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String[] splitWord(){

        return new String[0];
    }

    /**
     * 查找数据是否在索引中存在
     * @param name
     * @return
     */
    public String find(String name){
        String result = null;
        //将名字拆解
        name = name.replaceAll("·","");
        char[] chars =  name.toCharArray();
        Map<String,Map> paramTemp = param ;
        for (int k=0;k<chars.length;k++) {
            String key = String.valueOf(chars[k]);

//            if (k == 0) {
                //当是第一个字符时,查询第一层hash,将下一层的map付给临时map
                paramTemp = paramTemp.get(key);
                if (paramTemp == null) {
                    result=k + "层查找不到!" + key;
                    System.out.println(result);
                    break;
                }

//            } else {
//                paramTemp = paramTemp.get(key);
//                if (paramTemp == null) {
//                    System.out.println(k + "层查找不到!" + key);
//                    break;
//                }
//            }
        }
        return result;
        }

    }
//}
