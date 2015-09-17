package mobilesafe66.tangxin.club.mobilesafe66;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testSepup3Activity(){
        System.out.print("测试中。。。。。。。。。。。。。。。。。。。。。");
        Log.v("test","测试中。。。。。。。。。。。。。。。。。。。。。");

        assertEquals("5","4");

        //测试一类  static的函数还好 直接在这掉   怎么做到直接跳到某个Activity中测
    }
}