package com.mafei.laboratory.commons.utils;

/**
 * @Author: HuangXuan
 * @CreateTime: 2025-07-07
 * @Description:
 * @email hxtxyydsa@gmail.com; 3441578327@qq.com
 * @Version: 1.0
 */
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * 百度人脸对比工具类
 */
public class FaceMatchUtil {
    private static final String FACE_MATCH_URL = "https://aip.baidubce.com/rest/2.0/face/v3/match";

    /**
     * 读取本地图片，转 Base64 字符串
     */
    public static String imageToBase64(String imagePath) {
        try {
            File file = new File(imagePath);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
            inputStream.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对比两张人脸图片，返回相似度得分
     * @param imagePath1 本地图片1路径
     * @param imagePath2 本地图片2路径
     * @param accessToken 百度 Access Token
     * @return 相似度得分（0-100之间）
     */
    public static double compareFaces(String imagePath1, String imagePath2, String accessToken) {
        try {
            String base64Image1 = imageToBase64(imagePath1);
            String base64Image2 = imageToBase64(imagePath2);

            List<Map<String, Object>> images = new ArrayList<>();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", base64Image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", base64Image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");

            images.add(map1);
            images.add(map2);

            Gson gson = new Gson();
            String param = gson.toJson(images);

            String result = HttpUtil.post(FACE_MATCH_URL, accessToken, "application/json", param);
            System.out.println("百度返回结果：" + result);

            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            int errorCode = jsonObject.get("error_code").getAsInt();
            if (errorCode == 0) {
                return jsonObject.getAsJsonObject("result").get("score").getAsDouble();
            } else {
                System.err.println("接口请求失败，错误码：" + errorCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 出错时返回 -1
    }
}
