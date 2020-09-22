package com.kingstar.bw.ml;

import com.kingstar.bw.exception.PlatException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: meitao
 * @Description: ${description}
 * @Date: 20-8-24 下午5:01
 * @Version: 1.0
 */
public class LevenshteinDistance {

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * 查询最短编辑距离
     *
     * @param src
     * @param dst
     * @return
     */
    public static int computeLevenshteinDistance(CharSequence src, CharSequence dst) {
        if (src==null){
            throw new PlatException("src不能为空!");
        }
        if (dst==null){
            throw new PlatException("dst不能为空!");
        }

        int[][] distance = new int[src.length() + 1][dst.length() + 1];

        for (int i = 0; i <= src.length(); i++)
            distance[i][0] = i;
        for (int j = 0; j <= dst.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= src.length(); i++) {
            for (int j = 1; j <= dst.length(); j++) {
                int flag = (src.charAt(i - 1) == dst.charAt(j - 1)) ? 0 : 1;
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + flag);
            }
        }
        return distance[src.length()][dst.length()];
    }


    /**
     * 利用滚动数组优化过的最小编辑距离算法。空间复杂度为O(2×min(lenSrc,lenDst))
     *
     * @param src 动态规划数组的行元素
     * @param dst 动态规划数组的列元素
     * @return
     */
    public static int computeLevenshteinDistance_Optimized(CharSequence src, CharSequence dst) {
        int lenSrc = src.length() + 1;
        int lenDst = dst.length() + 1;

        CharSequence newSrc = src;
        CharSequence newDst = dst;
        //如果src长度比dst的短，表示数组的列数更多，此时我们
        //交换二者的位置，使得数组的列数变为较小的值。
        if (lenSrc < lenDst) {
            newSrc = dst;
            newDst = src;
            int temp = lenDst;
            lenDst = lenSrc;
            lenSrc = temp;
        }

        //创建滚动数组，此时列数为lenDst，是最小的
        int[] cost = new int[lenDst];   //当前行依赖的上一行数据
        int[] newCost = new int[lenDst];//当前行正在修改的数据

        //对第一行进行初始化
        for (int i = 0; i < lenDst; i++)
            cost[i] = i;

        for (int i = 1; i < lenSrc; i++) {
            //对第一列进行初始化
            newCost[0] = i;

            for (int j = 1; j < lenDst; j++) {
                int flag = (newDst.charAt(j - 1) == newSrc.charAt(i - 1)) ? 0 : 1;

                int cost_insert = cost[j] + 1;        //表示“上面”的数据，即对应d(i - 1,j)
                int cost_replace = cost[j - 1] + flag;//表示“左上方的数据”，即对应d(i - 1,j - 1)
                int cost_delete = newCost[j - 1] + 1; //表示“左边的数据”，对应d(i,j - 1)

                newCost[j] = minimum(cost_insert, cost_replace, cost_delete); //对应d(i,j)
            }

            //把当前行的数据交换到上一行内
            int[] temp = cost;
            cost = newCost;
            newCost = temp;
        }

        return cost[lenDst - 1];
    }

    /**
     * 计算出匹配度
     * M=(1-d(src,dst))/max(src,dst),max(src,dst)为两个字符串中最长的,d(src,dst)为两个字符串之间的最短编辑距离
     *
     * @param src
     * @param dst
     * @return
     */
    public static BigDecimal computeLevenshteinDistanceRate(CharSequence src, CharSequence dst) {
        int distance = computeLevenshteinDistance_Optimized(src, dst);
        int max = Math.max(src.length(), dst.length());
        BigDecimal result = new BigDecimal(distance).divide(new BigDecimal(max),2,BigDecimal.ROUND_HALF_UP);
        return new BigDecimal("1").subtract(result);

    }
}
