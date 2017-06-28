package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;


/**
 * 闪屏页子线程休眠3秒后判断登陆状态，跳转至相应的Activity。
 * 时间： 2017年6月28日13:28:51
 * */

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (SharedPreferenceUtils.getBoolean("loginInfo", true, getApplicationContext())) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //finish();
            }else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(0);
            }
        }.start();


    }
}
