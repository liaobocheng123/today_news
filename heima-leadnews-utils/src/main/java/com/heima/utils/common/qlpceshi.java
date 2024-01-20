package com.heima.utils.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author QLP
 * @version 1.0
 * @date 2021/9/4 14:03
 */
public class qlpceshi {
    
    private  static  final ObjectMapper mapper=new ObjectMapper();

    public static void main(String[] args) {
        /*String value = "{\"name\":\"11\",\"age\":22}";
        Map map = JSON.parseObject(value, Map.class);
        System.out.println(map);
        System.out.println(map.get("111"));
        System.out.println(map.get("222"));*/

        Map<String,Object> maps=new HashMap();
        maps.put("abc","123");
        maps.put("def",456);
        try {
            String res = mapper.writeValueAsString(maps);
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }
}
