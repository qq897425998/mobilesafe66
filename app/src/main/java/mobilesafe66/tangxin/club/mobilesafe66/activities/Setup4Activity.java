package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import mobilesafe66.tangxin.club.mobilesafe66.R;
import mobilesafe66.tangxin.club.mobilesafe66.utils.PrefUtils;

/**
 *总开关
 */
public class Setup4Activity extends BaseSetupActivity {
    private CheckBox cbCheck;

    private void assignViews() {
        cbCheck = (CheckBox) findViewById(R.id.cb_check);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        assignViews();

        PrefUtils.setBoolean(getApplicationContext(), "protected", true);
        cbCheck.setChecked(true);
        cbCheck.setText("防盗保已开启");

        cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PrefUtils.setBoolean(getApplicationContext(), "protected", true);
                    cbCheck.setText("防盗保已开启");
                }else{
                    PrefUtils.setBoolean(getApplicationContext(), "protected", false);
                    cbCheck.setText("防盗保已关闭");
                }
            }
        });
    }

    @Override
    public void showNext() {
        PrefUtils.setBoolean(this,"configed",true);
        finish();
        startActivity(new Intent(this, LostAndFoundActivity.class));

        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    public void showPrevious() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_previous_in, R.anim.anim_previous_out);
    }
}
