package com.heima.common.baiduyun;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aip")
public class GreenTextScan {
    //设置APPID/AK/SK
    private String APP_ID = "45801203";
    private String API_KEY = "4mhcqGGy69RfsybrMQRdyFZD";
    private String SECRET_KEY = "3OilFbnlxZhoASu4AtnxwUXyL3n3RF1X";

    public Map greenTextScan(String content) throws JSONException {
        // 初始化一个AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        Map<String, String> resultMap = new HashMap<>();
        JSONObject res = client.textCensorUserDefined(content);
        System.out.println(res.toString(2));
        //返回的响应结果
        JSONObject jsonObject = new JSONObject(String.valueOf(res));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, jsonObject.get(key));
        }
//        获得特殊字段
        String conclusion = (String) map.get("conclusion");

        if (conclusion.equals("合规")) {
            resultMap.put("conclusion", conclusion);
            return resultMap;
        }
//        获得特殊集合字段
        JSONArray dataArrays = res.getJSONArray("data");
        String msg = "";
        // 将JSONArray转换为List<JSONObject>
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (int i = 0; i < dataArrays.length(); i++) {
            jsonObjectList.add(dataArrays.getJSONObject(i));
        }
        for (JSONObject result : jsonObjectList) {
            //获得原因
            msg += result.getString("msg");
        }
        resultMap.put("conclusion", conclusion);
        resultMap.put("msg", msg);
        return resultMap;
    }
}