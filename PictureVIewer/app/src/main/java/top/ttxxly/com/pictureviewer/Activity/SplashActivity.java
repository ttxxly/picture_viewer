package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        if (SharedPreferenceUtils.getBoolean("loginInfo", true, this)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}
