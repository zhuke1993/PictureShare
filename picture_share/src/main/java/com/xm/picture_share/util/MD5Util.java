package com.xm.picture_share.util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 采用MD5加密
 */
public class MD5Util {

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] b = messageDigest.digest(inStr.getBytes());
        return new BASE64Encoder().encode(b);
    }


    public static void main(String args[]) throws NoSuchAlgorithmException {
        System.out.println(new MD5Util().string2MD5("zhuke"));
        System.out.println(new MD5Util().string2MD5("zhuke"));
    }
}
