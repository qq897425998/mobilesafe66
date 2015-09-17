package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mobilesafe66.tangxin.club.mobilesafe66.R;
import mobilesafe66.tangxin.club.mobilesafe66.utils.StreamUtils;

/**
 * -展示logo，公司品牌
 * -检查版本更新
 * -项目初始化
 * -校验合法性(检查是否有网络，检查是否登录)
 *
 * 1.布局文件
 * 2.获取版本，显示给TextView
 * 3.访问服务器,获取Json数据
 * 4.解析Json数据判断是否有更新
 * 5.有更新跳弹窗提示
 * 6.无更新跳主页面
 * 7.网络异常等情况，也跳主页面
 * 8.闪屏页显示2秒逻辑
 * 9.打包2.0版本
 * 10.使用xutils下载apk
 * 11.更新下载进度
 * 12安装apk
 * 13.解决签名冲突问题
 *
 */
public class SplashActivity extends Activity {
    private static final int CODE_UPDATE_DIALOG = 1;
    private static final int CODE_ENTER_HOME = 2;
    private static final int CODE_NETWORK_ERROR = 3;
    private static final int CODE_JSON_ERROR = 4;

    private TextView tvName;
    private ProgressBar pbLoading;
    private TextView tvProcess;
    private RelativeLayout rl_root;

    String mVersionName;
    int mVersionCode;
    String mDes;
    String mUrl;


    private void assignViews() {
        tvName = (TextView) findViewById(R.id.tv_name);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        tvProcess = (TextView) findViewById(R.id.tv_process);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        assignViews();

        setVersionName();
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean autoUpdate = sp.getBoolean("auto_update",true);

        if(autoUpdate){
            checkVersion();
        }else{
            mHanlder.sendEmptyMessageDelayed(CODE_ENTER_HOME,2000);
        }


        AlphaAnimation anim = new AlphaAnimation(0.2f,1);
        anim.setDuration(2000);
        rl_root.startAnimation(anim);

    }

    private Handler mHanlder = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;
                case CODE_NETWORK_ERROR:
                    Toast.makeText(getApplicationContext(), "没网或 服务机没开", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(SplashActivity.this, "Json解析错误", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
            }
        }
    };

    private void enterHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void checkVersion() {
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                long accessTime = 0;

                try {
                    long startTime = System.currentTimeMillis();
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://10.0.2.2/first/update.json").openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(2000);
                    conn.setReadTimeout(2000);
                    conn.connect();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream in = conn.getInputStream();

                        String result = StreamUtils.stream2string(in);

                        JSONObject jo = new JSONObject(result);
                        mVersionName = jo.getString("versionName");
                        mVersionCode = jo.getInt("versionCode");
                        mDes = jo.getString("dec");
                        mUrl = jo.getString("url");

                        Log.v("json", Integer.toString(mVersionCode));

                        if (mVersionCode > getVersionCode()) {
                            msg.what = CODE_UPDATE_DIALOG;
                        } else {
                            msg.what = CODE_ENTER_HOME;
                        }
                    }
                    long endTime = System.currentTimeMillis();
                    accessTime = endTime - startTime;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = CODE_NETWORK_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = CODE_JSON_ERROR;
                } finally {
                    try {

                        Thread.sleep(2000 - accessTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mHanlder.sendMessage(msg);
                }
                super.run();
            }
        }.start();
    }

    protected void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本" + mVersionName);
        builder.setMessage(mDes + "下载地址" + mUrl);
        builder.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk();
            }
        });
        builder.setNegativeButton("以后在说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });

        builder.show();
    }

    private void downloadApk() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            tvProcess.setVisibility(TextView.VISIBLE);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mobilesafe.apk";
            HttpUtils utils = new HttpUtils();
            utils.download(mUrl, path, new RequestCallBack<File>() {

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);

                    int percent = (int) (100 * current / total);

                    tvProcess.setText(Integer.toString(percent) + "%");
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                    startActivityForResult(i, 0);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    e.printStackTrace();
                    Toast.makeText(SplashActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(this, "SD卡没有挂载", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }

    private void setVersionName() {
        tvName.setText("版本号:" + getVersionName());
    }

    private int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @return 获取包名
     */
    private String getVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
