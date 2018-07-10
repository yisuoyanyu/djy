package com.frame.base.utils;

import java.util.Random;


/**
 * @author: guoqq
 * @date: 2016/3/19
 * @version: v1.0
 * @description:
 */
public class StringRandomUtil {

	
	/**
	 * 生成随机数字和字母
	 * @param length
	 * @return
	 */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    
    /**
     * 生成随机数字
     * @param size
     * @return
     */
    public static String randomInt(int size) {
        if(size>10) size=6;
        int temp = 1;
        for (int i = 1; i < size; i++) {
            temp *= 10;
        }
        double random = (Math.random()*9+1)*temp;
        return String.format("%f", random).substring(0,size);
    }

    /**
     * 生成随机字母
     * @param size
     * @return
     */
    public static String randomABC(int size) {
        String str = "";
        for (int i = 0; i < size; i++) {
            char c = 'a';
            c = (char) (c + (int) (Math.random() * 26));
            str = str + c;
        }
        return str;
    }
	
}
