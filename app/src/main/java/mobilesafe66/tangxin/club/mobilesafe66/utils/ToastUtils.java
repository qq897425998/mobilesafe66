package mobilesafe66.tangxin.club.mobilesafe66.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 89742 on 11-Aug-15.
 */
public class ToastUtils {

    public static void showToast(Context ctx,String text){
        Toast.makeText(ctx,text,Toast.LENGTH_SHORT).show();
    }
}
