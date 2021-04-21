package com.jfy.common.redisson.module.utils;

import java.util.StringJoiner;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 15:44 2021/3/23
 */
public class ShardingSqlUtil {
    public static void main(String[] args) {

        //统计会话

//        System.out.println("-- 公众号会话");
//        System.out.println(joinCountSql("t_dialog", 128, " count(1) num ", " create_time >= '2020-12-01 00:00:00' and create_time < '2021-01-01 00:00:00' and from_type = 1  "));
//        System.out.println("-- H5会话");
//        System.out.println(joinCountSql("t_dialog", 128, " count(1) num ", " create_time >= '2020-12-01 00:00:00' and create_time < '2021-01-01 00:00:00' and from_type = 2  "));
//        System.out.println("-- 小程序会话");
//        System.out.println(joinCountSql("t_dialog", 128, " count(1) num ", " create_time >= '2020-12-01 00:00:00' and create_time < '2021-01-01 00:00:00' and from_type = 3  "));
//
//        System.out.println("-- 公众号店铺");
//        System.out.println(joinCountSql("t_dialog", 128, " count(distinct pid) num ", " create_time >= '2020-12-01 00:00:00' and create_time < '2021-01-01 00:00:00' and from_type = 1  "));
//        System.out.println("-- H5店铺");
//        System.out.println(joinCountSql("t_dialog", 128, " count(distinct pid) num ", " create_time >= '2020-12-01 00:00:00' and create_time < '2021-01-01 00:00:00' and from_type = 2  "));
//        System.out.println("-- 小程序店铺");
//        System.out.println(joinCountSql("t_dialog", 128, " count(distinct pid) num ", " create_time >= '2020-12-01 00:00:00' and create_time < '2021-01-01 00:00:00' and from_type = 3  "));
//
//        System.out.println("-- 所有店铺");
//        System.out.println(joinCountSql("t_dialog", 128, " count(distinct pid) num ", " create_time >= '2020-12-01 00:00:00' and create_time < '2021-01-01 00:00:00' "));



        // 查询粉丝

//        System.out.println(joinCountSql("t_fan_account",128, " * "," wid = 1501318639 "));


        System.out.println(joinCountSql("t_dialog",128," count(1) as num "," create_time <= '2021-04-13 01:00:00' and create_time > '2021-04-12 01:00:00' and STATUS <= 1 "));
    }

    public static String joinCountSql(String tableName, int num, String fields, String whereStr) {
        StringJoiner joiner = new StringJoiner("\n union all ", "select sum(num) from (\n ", "\n ) demo;");
        for (int i = 0; i < num; i++) {
            joiner.add("select " + fields + " from " + tableName + "_" + i + " where " + whereStr);
        }
        return joiner.toString();
    }


}
