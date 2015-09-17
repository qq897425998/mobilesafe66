package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mobilesafe66.tangxin.club.mobilesafe66.R;
import mobilesafe66.tangxin.club.mobilesafe66.utils.PrefUtils;

/**
 * 手机防盗页面
 */
public class LostAndFoundActivity extends Activity {
    private TextView tvPhone;
    private ImageView ivIcon;
    private void assignViews() {
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean configed = PrefUtils.getBoolean(this,"configed",false);

        if(!configed){
            startActivity(new Intent(this,Setup1Activity.class));
            finish();
        }else
        {
            setContentView(R.layout.activity_lost_and_found);
            assignViews();
            String phone = PrefUtils.getString(this,"safe_phone","无");
            tvPhone.setText(phone);

            boolean aProtected = PrefUtils.getBoolean(this, "protected", false);
            if (aProtected){
                ivIcon.setImageResource(R.drawable.lock);
            }else {
                ivIcon.setImageResource(R.drawable.unlock);
            }
        }

    }

    public void reSetup(View v){
        startActivity(new Intent(this,Setup1Activity.class));
    }
}
