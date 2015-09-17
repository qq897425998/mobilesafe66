package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import mobilesafe66.tangxin.club.mobilesafe66.R;
import mobilesafe66.tangxin.club.mobilesafe66.utils.PrefUtils;
import mobilesafe66.tangxin.club.mobilesafe66.utils.ToastUtils;

/**
 * 选择联系人
 */
public class Setup3Activity extends BaseSetupActivity {
    private EditText etPhone;
    private Button btnSelect;
    private ImageView imageView1;

    private void assignViews() {
        etPhone = (EditText) findViewById(R.id.et_phone);
        btnSelect = (Button) findViewById(R.id.btn_select);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        assignViews();

        String phone = PrefUtils.getString(this,"safe_phone","");
        etPhone.setText(phone);
    }

    @Override
    public void showNext() {

        String phone = etPhone.getText().toString().trim();

        if(TextUtils.isEmpty(phone)){
            ToastUtils.showToast(this,"不能为空");
            return;
        }
        if(phone.length() != 11){
            ToastUtils.showToast(this,"手机号位数错误");
            return;
        }

        PrefUtils.setString(this,"safe_phone",phone);
        finish();
        startActivity(new Intent(this, Setup4Activity.class));

        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    public void showPrevious() {

        finish();
        startActivity(new Intent(this, Setup2Activity.class));

        overridePendingTransition(R.anim.anim_previous_in, R.anim.anim_previous_out);
    }

    public void onClickSelect(View v){
        startActivityForResult(new Intent(this,ContactActivity.class),0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(data == null) return; //避免用户直接返回,导致空指针异常  在用户点返回键也会掉onActivityResult
        String phone = data.getStringExtra("phone");
        etPhone.setText(phone);
    }
}
