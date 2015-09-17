package mobilesafe66.tangxin.club.mobilesafe66.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import mobilesafe66.tangxin.club.mobilesafe66.utils.PrefUtils;
import mobilesafe66.tangxin.club.mobilesafe66.utils.ToastUtils;

/**
 * 手机定位服务
 * Created by 89742 on 09-Sep-15.
 */
public class LocationService extends Service {

    private LocationManager mLM;
    private MyListener mListener;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //初始化标准
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);//允许花费流量3G定位

        //String bestProvider = "no provider";
        String bestProvider = mLM.getBestProvider(criteria,true); //参1标准 参2是否可用
        Log.v("log",bestProvider);

        ToastUtils.showToast(getApplicationContext(), "正在获取经纬度");

        mListener = new MyListener();
        mLM.requestLocationUpdates(bestProvider,0,0,mListener);
    }

    class MyListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            String j = "j:" + location.getLongitude();
            String w = "w:" + location.getLatitude();
            String accuracy = "accuracy:" + location.getAccuracy();

            String result = j + "\n" + w + "\n" + accuracy;

            // 发送经纬度给安全号码
            String phone = PrefUtils.getString(getApplicationContext(),"safe_phone","");
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(phone,null,result,null,null);
            stopSelf();// 服务自杀的方法
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            ToastUtils.showToast(getApplicationContext(),"状太改变");
        }

        @Override
        public void onProviderEnabled(String provider) {
            ToastUtils.showToast(getApplicationContext(),"GPS打开");

        }

        @Override
        public void onProviderDisabled(String provider) {
            ToastUtils.showToast(getApplicationContext(),"GPS关闭");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLM.removeUpdates(mListener);
        mListener = null;
    }
}
