package com.le.share.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Md5Util {

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n < b.length - 1) //ignore ":"
            // hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    public static String bytesToHexString(byte[] bytes) {
        if (null == bytes)
            return "";
        int len = bytes.length;
        StringBuffer sb = new StringBuffer(len * 2);
        Formatter formatter = new Formatter(sb);
        for (int i = 0; i < len; i++) {
            formatter.format("%02x", bytes[i]);
        }
        formatter.close();
        return sb.toString().toLowerCase();
    }

    public static String generateMD5String(String srcString) {
        if (srcString == null) {
            return "";
        }
        String hash = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            if (digest != null) {
                digest.update(srcString.getBytes());
                hash = byte2hex(digest.digest());
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return hash;
    }

}
