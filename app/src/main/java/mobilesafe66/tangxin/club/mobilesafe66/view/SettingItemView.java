package mobilesafe66.tangxin.club.mobilesafe66.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobilesafe66.tangxin.club.mobilesafe66.R;


/**
 * Created by 89742 on 10-Aug-15.
 */
public class SettingItemView extends RelativeLayout {
    public static String NAMESPACE = "http://www.tangxin.club/mobilesafe66.tangxin.club.mobilesafe66";
    TextView tvTitle;
    TextView tvDesc;
    CheckBox cbCheck;
    String mDesc_Off;
    String mDesc_On;
    String mTitle;
    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        int count = attrs.getAttributeCount();
//        for(int i=0; i < count; i++){
//                Log.v("log", attrs.getAttributeName(i) +":"+ attrs.getAttributeValue(i));
//        }
        mTitle = attrs.getAttributeValue(NAMESPACE,"titles");
        mDesc_Off = attrs.getAttributeValue(NAMESPACE,"desc_off");
        mDesc_On = attrs.getAttributeValue(NAMESPACE,"desc_on");

        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    /**
     * 初始化布局
     */
    private void initView(){

        View child = View.inflate(getContext(), R.layout.setting_item_view,null);
        
        tvTitle = (TextView) child.findViewById(R.id.tv_title);
        tvDesc = (TextView) child.findViewById(R.id.tv_desc);
        cbCheck = (CheckBox) child.findViewById(R.id.cb_check);

        tvTitle.setText(mTitle);

        this.addView(child);
    }
    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setDesc(String desc){
        tvDesc.setText(desc);
    }

    public void setCheck(boolean check){
        setDesc((check) ? mDesc_On : mDesc_Off);
        cbCheck.setChecked(check);
    }
    public boolean isChecked(){
        return cbCheck.isChecked();
    }
}
