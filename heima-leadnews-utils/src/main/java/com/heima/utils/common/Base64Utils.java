package com.heima.utils.common;
import java.util.Base64;

public class Base64Utils {

    /**
     * 解码
     * @param base64
     * @return
     */
    public static byte[] decode(String base64){
        try {
            // Base64解码
            return Base64.getDecoder().decode(base64);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 编码
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
