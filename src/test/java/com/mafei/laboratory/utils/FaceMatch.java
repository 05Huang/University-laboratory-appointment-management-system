package com.mafei.laboratory.utils;

/**
 * @Author: HuangXuan
 * @CreateTime: 2025-07-07
 * @Description:
 * @email hxtxyydsa@gmail.com; 3441578327@qq.com
 * @Version: 1.0
 */


import com.mafei.laboratory.commons.utils.GsonUtils;
import com.mafei.laboratory.commons.utils.HttpUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * 人脸对比
 */
public class FaceMatch {

    /**
     * 读取本地图片转 Base64
     */
    public static String encodeImageToBase64(String imagePath) {
        try {
            File file = new File(imagePath);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
            inputStream.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String faceMatch(String imagePath1, String imagePath2) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {

            // 把本地图片转 Base64
            String base64Image1 = encodeImageToBase64(imagePath1);
            String base64Image2 = encodeImageToBase64(imagePath2);
            // 构造参数
            List<Map<String, Object>> images = new ArrayList<>();

            // 第一张图片
            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", base64Image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");   // LIVE、IDCARD、WATERMARK、CERT
            map1.put("quality_control", "LOW");  // 图片质量控制
            map1.put("liveness_control", "NONE");  // 活体检测控制

            // 第二张图片
            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", base64Image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NONE");

            // 把两张图加进去
            images.add(map1);
            images.add(map2);
            String param = GsonUtils.toJson(images);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.1707af07b45ca7198a5a78ddd69f76ae.2592000.1754443880.282335-119434658";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // 本地图片路径（换成你的）
        String imagePath1 = "E:\\laboratory-master\\src\\test\\resources\\images\\yuan1.png";
        String imagePath2 = "E:\\laboratory-master\\src\\test\\resources\\images\\yuan2.png";
        faceMatch(imagePath1, imagePath2);
    }
}
