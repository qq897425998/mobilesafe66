package mobilesafe66.tangxin.club.mobilesafe66.utils;

import android.content.Context;
import android.content.SharedPreferences;

/** 对SharePreference的封装
 * Created by 89742 on 11-Aug-15.
 */
public class PrefUtils  {
    public static boolean getBoolean(Context ctx, String key, boolean defValue){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    public static void setBoolean(Context ctx, String key, boolean value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static void setString(Context ctx, String key, String value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
    public static String getString(Context ctx,String key,String defValue){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public static void remove(String key, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
}
