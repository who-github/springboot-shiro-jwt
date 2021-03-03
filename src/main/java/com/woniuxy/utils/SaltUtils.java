package com.woniuxy.utils;

import lombok.Data;

import java.util.Random;

//MD5加密需要使用到的盐类
@Data
public class SaltUtils {

    public static String getSalt(int count){
        char[] chars = "QWERTYUIOP[]ASDFGHJKL;'ZXCVBNM@#$%^&*()qwertyuiopasdfghjklzxcvbnm".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0 ; i < count ;i++){
            char aChar = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(aChar);
        }
        return stringBuilder.toString();
    }

}
