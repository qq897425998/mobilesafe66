package mobilesafe66.tangxin.club.mobilesafe66.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 89742 on 12-Aug-15.
 */
public class MD5Utils {

    public static String encode(String str) {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(str.getBytes());

            StringBuffer sb = new StringBuffer();
            for(byte b:bytes){
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if(hexString.length() == 1){
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            String md5 = sb.toString();
            return md5;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
