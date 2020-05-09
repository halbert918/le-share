package com.le.share.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class IpUtil {

    public static String getIP() {
        InetAddress address = getIPAddress();
        if (address == null) {
            return "";
        }
        return address.getHostAddress();
    }

    public static InetAddress getIPAddress() {
        try {
            //获取本地IP对象
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMacAddress(InetAddress ia)throws Exception {
        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

        //下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();

        for(int i = 0;i < mac.length; i++){
            if(i != 0){
                sb.append("-");
            }
            //mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            System.err.println(s);

            sb.append(s.length() == 1 ? 0+s : s);
        }

        //把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }


}
