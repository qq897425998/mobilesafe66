package mobilesafe66.tangxin.club.mobilesafe66.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import mobilesafe66.tangxin.club.mobilesafe66.R;
import mobilesafe66.tangxin.club.mobilesafe66.utils.MD5Utils;
import mobilesafe66.tangxin.club.mobilesafe66.utils.PrefUtils;
import mobilesafe66.tangxin.club.mobilesafe66.utils.ToastUtils;

/**
 * Created by 89742 on 8/6/2015.
 * 程序主页面
 */
public class HomeActivity extends Activity {

    private String[] mHomeNames = new String[]{ "手机防盗", "通讯卫士", "软件管理",
            "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};

    private int[] mImageIds = new int[]{
            R.drawable.home_apps,
            R.drawable.home_callmsgsafe,
            R.drawable.home_netmanager,
            R.drawable.home_safe,
            R.drawable.home_settings,
            R.drawable.home_sysoptimize,
            R.drawable.home_taskmanager,
            R.drawable.home_sysoptimize,
            R.drawable.home_tools};

    private TextView tvHome;
    private GridView gvHome;

    private void assignViews() {
        tvHome = (TextView) findViewById(R.id.tv_home);
        gvHome = (GridView) findViewById(R.id.gvHome);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        assignViews();
        gvHome.setAdapter(new HomeAdapter());

        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showSafeDialog();
                        break;
                    case 8:
                        startActivity(new Intent(getApplicationContext(),SettingActivity.class));
                        break;
                }
            }
        });

    }

    private void showSafeDialog() {
        String pwd = PrefUtils.getString(this,"password",null);

        if(pwd == null){
            showSetPwdDialog();
        }else {
            showInputPwdDialog();
        }
    }

    /**
     * 输入密码 对话框
     */
    private void showInputPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_input_pwd, null);

        final TextView tvPwd = (TextView) view.findViewById(R.id.et_pwd);

        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = MD5Utils.encode( tvPwd.getText().toString().trim() );

                String savePwd = PrefUtils.getString(getApplicationContext(),"password",null);

                if(TextUtils.isEmpty(pwd)){
                    ToastUtils.showToast(getApplicationContext(),"密码不能为空");
                }else if(!pwd.equals(savePwd)){
                    ToastUtils.showToast(getApplicationContext(),"密码错误");
                }else {
                    ToastUtils.showToast(getApplicationContext(),"密码正确");
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), LostAndFoundActivity.class));
                }
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();

    }

    /**
     * 设置密码 对话框
     */
    private void showSetPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_set_pwd, null);

        final TextView tvPwd = (TextView) view.findViewById(R.id.et_pwd);
        final TextView tvPwdConfirm = (TextView) view.findViewById(R.id.et_pwd_confirm);


        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = MD5Utils.encode( tvPwd.getText().toString().trim() );
                String pwdConfirm = MD5Utils.encode( tvPwdConfirm.getText().toString().trim() );

                if(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdConfirm)){
                    ToastUtils.showToast(getApplicationContext(),"密码不能为空");
                }else if( !pwd.equals(pwdConfirm) ){
                    ToastUtils.showToast(getApplicationContext(),"两次密码不相同");
                }else{
                    ToastUtils.showToast(getApplication(),"密码设置成功");
                    PrefUtils.setString(getApplicationContext(),"password",pwd);
                    dialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }

    class HomeAdapter extends BaseAdapter {

        @Override

        public int getCount() {
            return mHomeNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),R.layout.list_item_home,null);

            TextView tvText = (TextView) view.findViewById(R.id.tv_text);
            ImageView ivImage= (ImageView) view.findViewById(R.id.iv_icon);

            tvText.setText(mHomeNames[position]);
            ivImage.setImageResource(mImageIds[position]);
            return view;
        }
    }
}

