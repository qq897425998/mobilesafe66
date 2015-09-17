package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import mobilesafe66.tangxin.club.mobilesafe66.R;
import mobilesafe66.tangxin.club.mobilesafe66.utils.PrefUtils;
import mobilesafe66.tangxin.club.mobilesafe66.view.SettingItemView;

public class SettingActivity extends Activity {
    private SettingItemView sivUpdate;
    private void assignViews() {
        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        assignViews();


        boolean autoUpdate = PrefUtils.getBoolean(this,"auto_update", true);


        if(autoUpdate){
            sivUpdate.setCheck(true);
        }else{
            sivUpdate.setCheck(false);
        }
        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sivUpdate.isChecked()) {
                    sivUpdate.setCheck(false);
                    PrefUtils.setBoolean(getApplicationContext(),"auto_update",false);
                } else {
                    sivUpdate.setCheck(true);
                    PrefUtils.setBoolean(getApplicationContext(), "auto_update", true);
                }
            }
        });
    }

}
