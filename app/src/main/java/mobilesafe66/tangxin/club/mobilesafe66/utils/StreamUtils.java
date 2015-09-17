package mobilesafe66.tangxin.club.mobilesafe66.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 89742 on 8/5/2015.
 */
public class StreamUtils {
    public static String stream2string(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte [] bytes = new byte[1024];
        int len;
        while((len = in.read(bytes)) != -1){
            out.write(bytes,0,len);
        }

        return out.toString();
    }
}
