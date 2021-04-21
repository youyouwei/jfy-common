package com.jfy.common.redisson.module.utils;

import com.google.common.base.Splitter;
import com.google.common.io.Files;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 14:11 2021/4/2
 */
public class DocumentUtil {

    public static void main(String[] args) throws Exception {

//        parseLog("C:\\Users\\Administrator\\Desktop\\0401-log.txt");

//        decodeUrl("https://cn.bing.com/?mkt=zh-CN");


        System.out.println("1111");


        System.in.read();

    }

    public static void parseLog(final String filePath) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                List<String> list = Files.readLines(file, Charset.defaultCharset());
                List<String> newList = list.stream()
                        .map(u -> u.substring(u.indexOf("{"), u.indexOf("}", u.indexOf("{") + 1) + 1))
                        .distinct()
                        .collect(Collectors.toList());

                newList.forEach(System.out::println);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void decodeUrl(String url) throws Exception {

        String params = url.substring(url.indexOf("?") + 1);
        Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=").split(params);
        if (map.get("shareh5url") != null) {
            url = URLDecoder.decode(map.get("shareh5url"), "utf-8");
        }


        System.out.println(url);

    }
}
