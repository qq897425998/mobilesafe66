package mobilesafe66.tangxin.club.mobilesafe66.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import mobilesafe66.tangxin.club.mobilesafe66.utils.PrefUtils;

/**
 * Created by 89742 on 18-Aug-15.
 * 启动后 检测SIM卡 是否被换
 *
 * 开启重启广播接收者 需要权限:android.permission.RECEIVE_BOOT_COMPLETED
 *
 * <receiver android:name=".receiver.BootCompleteReceiver" >
 *     <intent-filter>
 *          <action android:name="android.intent.action.BOOT_COMPLETED" />
 *      </intent-filter>
 * </receiver>
 */
public class BootCompleteReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"系统以重启完成",Toast.LENGTH_LONG).show();
        System.out.println("系统以重启");


        boolean aProtected = PrefUtils.getBoolean(context, "protected", false);
        if (!aProtected){
            return;
        }

        String bindSim = PrefUtils.getString(context, "bind_sim", "");

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String currentSim = tm.getSimSerialNumber();

        if(!bindSim.equals(currentSim)){
            System.out.println("SIM以更换");
            Toast.makeText(context,"SIM以更换",Toast.LENGTH_LONG).show();
            String savePhone = PrefUtils.getString(context, "safe_phone", "");

            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(savePhone,null,"sim changed",null,null);
        }
    }
}
