package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;

import mobilesafe66.tangxin.club.mobilesafe66.R;

/**
 * 欢迎使用界面
 */
public class Setup1Activity extends BaseSetupActivity {

    private GestureDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }


    public void showNext() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();

        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    public void showPrevious() {
    }

}
