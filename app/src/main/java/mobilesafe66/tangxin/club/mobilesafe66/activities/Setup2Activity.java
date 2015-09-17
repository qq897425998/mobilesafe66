package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.View;

import mobilesafe66.tangxin.club.mobilesafe66.R;
import mobilesafe66.tangxin.club.mobilesafe66.utils.PrefUtils;
import mobilesafe66.tangxin.club.mobilesafe66.utils.ToastUtils;
import mobilesafe66.tangxin.club.mobilesafe66.view.SettingItemView;

/**
 * SIM卡绑定
 */
public class Setup2Activity extends BaseSetupActivity {
    private SettingItemView sivBind;
    private GestureDetector mDetector;
    private void assignViews() {
        sivBind = (SettingItemView) findViewById(R.id.siv_bind);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        assignViews();

        final String bindSim = PrefUtils.getString(this,"bind_sim", null);
        if(TextUtils.isEmpty(bindSim)){
            sivBind.setCheck(false);
        }else{
            sivBind.setCheck(true);
        }
        sivBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sivBind.isChecked()){
                    sivBind.setCheck(false);
                    PrefUtils.remove("bind_sim", getApplicationContext());
                    PrefUtils.remove("device_Id",getApplicationContext());

                }else{
                    sivBind.setCheck(true);

                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String deviceId = tm.getDeviceId();

                    String simSerialNumber = tm.getSimSerialNumber();

                    PrefUtils.setString(getApplicationContext(),"bind_sim",simSerialNumber);
                    PrefUtils.setString(getApplicationContext(),"device_Id",deviceId);
                }
            }
        });


    }


    public void showNext() {

        String bind_sim = PrefUtils.getString(this, "bind_sim", null);

        if(TextUtils.isEmpty(bind_sim)){
            ToastUtils.showToast(this,"请先绑定SIM卡");
            return;
        }

        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    public void showPrevious() {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_previous_in, R.anim.anim_previous_out);
    }
}
