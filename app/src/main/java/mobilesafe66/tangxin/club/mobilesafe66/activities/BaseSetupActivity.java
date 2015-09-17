package mobilesafe66.tangxin.club.mobilesafe66.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import mobilesafe66.tangxin.club.mobilesafe66.utils.ToastUtils;

/**
 * Created by 89742 on 15-Aug-15.
 */
public abstract class BaseSetupActivity  extends Activity {
    private GestureDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if(Math.abs(e1.getRawY() - e2.getRawY()) > 200) {
                    ToastUtils.showToast(getApplicationContext(), "不能这像滑哦 只能左右滑动");
                    return false;
                }
                if(Math.abs(velocityX) < 200){
                    ToastUtils.showToast(getApplicationContext(),"滑动太慢，你是在屏幕摩擦吗");
                    return false;
                }

                if(e1.getRawX() - e2.getRawX() > 100){
                    showNext();
                }
                if(e2.getRawX() - e1.getRawX() > 100){
                    showPrevious();
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void onNext(View v){
        showNext();

    }
    public void onPrevious(View v){
        showPrevious();
    }

    abstract public void showNext();
    abstract public void showPrevious();
}
