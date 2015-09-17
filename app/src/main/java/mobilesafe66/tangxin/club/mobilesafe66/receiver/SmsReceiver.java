package mobilesafe66.tangxin.club.mobilesafe66.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import mobilesafe66.tangxin.club.mobilesafe66.R;
import mobilesafe66.tangxin.club.mobilesafe66.service.LocationService;

/**
 * Created by 89742 on 18-Aug-15.
 *
 *
 * 拦截短信   Android 4.4以后就不行了 只能有唯一一个默认的短息应用能读短信
 *
 * <receiver android:name=".receiver.SmsReceiver" > <intent-filter
 * android:priority="2147483647" > <action
 * android:name="android.provider.Telephony.SMS_RECEIVED" /> </intent-filter>
 * </receiver>
 *
 * 需要权限:<uses-permission android:name="android.permission.RECEIVE_SMS" />
 *
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objs = (Object[]) intent.getExtras().get("pdus");   //protocol description units
        Toast.makeText(context, "短息来了", Toast.LENGTH_LONG).show();
        System.out.println("短息来了");

        for (Object obj : objs ){
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);

            String messageBody = sms.getMessageBody(); //内容
            String originatingAddress = sms.getOriginatingAddress(); //号码

            System.out.println("号码:" + originatingAddress + ";内容:"
                    + messageBody);

            Toast.makeText(context,messageBody,Toast.LENGTH_LONG).show();

            if("#*alarm*#".equals(messageBody)){
                System.out.println("播放放报警音乐");

                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                player.setVolume(1f, 1f);// 音量最大, 基于系统音量的比值
                player.setLooping(true);// 单曲循环
                player.start();// 开始播放
                // 4.4+版本上,无法拦截短信, 调此方法没有, 比如当前应用时默认短信应用才可以
                // 操作短信数据库, 删除数据库相关短信内容, 间接达到删除短信目的
                abortBroadcast();
            }else if ("#*location*#".equals(messageBody)) {
                context.startService(new Intent(context, LocationService.class));
                abortBroadcast();// 中断短信传递
            }else if ("#*lockscreen*#".equals(messageBody)) {
                System.out.println("一键锁屏");
                abortBroadcast();// 中断短信传递
            } else if ("#*wipedata*#".equals(messageBody)) {
                System.out.println("清除数据");
                abortBroadcast();// 中断短信传递
            }
        }


    }
}
