package top.ttxxly.com.pictureviewer.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNickname;
    private EditText mPassword;
    private EditText mMobile;
    private Button mRegitser;
    private String s;
    private String URL = "http://10.0.2.2/picture_viewer";
    private Context mContext;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            s = msg.obj.toString();
            Log.i("S", s);
            User value = new Gson().fromJson(s, User.class);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                    SharedPreferenceUtils.putBoolean("UserStatus", true, getApplicationContext());
                    SharedPreferenceUtils.putString("UserId", value.getUserid(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserNickname", value.getNickname(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserPassword", value.getPassword(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserMobile", value.getMobile(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserPortrait", value.getPortrait(), getApplicationContext());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    break;
                case -1:
                    Toast.makeText(mContext, value.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = RegisterActivity.this;
        mNickname = (EditText) findViewById(R.id.et_register_nickname);
        mPassword = (EditText) findViewById(R.id.et_register_password);
        mMobile = (EditText) findViewById(R.id.et_register_mobile);
        mRegitser = (Button) findViewById(R.id.btn_register_register);
        ImageView mReturn = (ImageView) findViewById(R.id.img_register_return_top);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        mRegitser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = mNickname.getText().toString();
                String password = mPassword.getText().toString();
                String mobile = mMobile.getText().toString();

                if (TextUtils.isEmpty(nickname)) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    register();
                }
            }
        });
    }

    private void register() {
        //新建线程
        new Thread() {
            public void run() {
                try {
                    SendRequestToRegister();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void SendRequestToRegister() {

        HttpURLConnection conn = null;
        try {
            // 创建一个URL对象
            String url = URL + "/interface/register.php" + "?nickname=" + mNickname.getText().toString() + "&password=" + mPassword.getText().toString() + "&mobile=" + mMobile.getText().toString();
            Log.i("URl", url);
            URL mURL = new URL(url);
            // 调用URL的openConnection()方法,获取HttpURLConnection对象
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");// 设置请求方法为post
            conn.setReadTimeout(5000);// 设置读取超时为5秒
            conn.setConnectTimeout(10000);// 设置连接网络超时为10秒
            conn.setDoOutput(true);// 设置此方法,允许向服务器输出内容
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
            conn.setRequestProperty("Content-Type", "application/json");

            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {

                InputStream is = conn.getInputStream();
                String data = StreamUtils.Stream2String(is);

                Log.i("data", data);
                User value = new Gson().fromJson(data, User.class);
                String flat = value.getFlat();
                Message msg = new Message();
                if (flat.equals("success")) {
                    Log.i("Status", "注册成功" );
                    msg.what = 1;
                    msg.obj = data;

                }else {
                    msg.what = -1;
                    msg.obj = data;
                }
                handler.sendMessage(msg);
            } else {
                Log.i("访问失败", "responseCode");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }

    }
}
