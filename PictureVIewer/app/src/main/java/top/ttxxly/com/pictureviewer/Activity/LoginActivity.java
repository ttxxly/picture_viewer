package top.ttxxly.com.pictureviewer.Activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText et_nickname;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_register;
    private String s;   //保存返回的 JSON 数据
    final public String URL = "http://10.0.2.2/picture_viewer";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            switch (msg.what) {
                case 1:
                    s = msg.obj.toString();
                    Log.i("S", s);
                    User value = new Gson().fromJson(s, User.class);
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    SharedPreferenceUtils.putBoolean("loginInfo", true, getApplicationContext());
                    SharedPreferenceUtils.putString("loginNickname", value.getNickname(), getApplicationContext());
                    SharedPreferenceUtils.putString("loginPassword", value.getPassword(), getApplicationContext());
                    SharedPreferenceUtils.putString("loginMobile", value.getMobile(), getApplicationContext());

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_nickname = (EditText) findViewById(R.id.et_login_nickname);
        et_password = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        TextView mLook = (TextView) findViewById(R.id.tv_login_look_around);
        mLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "点击了登录按钮", Toast.LENGTH_SHORT).show();
                String nickname = et_nickname.getText().toString();
                String passwd = et_password.getText().toString();
                if (TextUtils.isEmpty(nickname)) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(passwd)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    StartRequestFromPHP();
                }
            }
        });
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }


    private void StartRequestFromPHP() {

        //新建线程
        new Thread() {
            public void run() {
                try {
                    SendRequestToLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void SendRequestToLogin() {
        User user = new User();
        String s = et_nickname.getText().toString();
        if (isMobile(s)) {
            user.setMobile(s);
        } else {
            user.setNickname(s);
        }
        user.setPassword(et_password.getText().toString());


        HttpURLConnection conn = null;
        try {
            // 创建一个URL对象
            String url = URL + "/interface/login.php" + "?nickname=" + user.getNickname() + "&password=" + user.getPassword() + "&mobile=" + user.getMobile();
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
                String message = value.getMessage();
                long userid = value.getUserid();
                String nickname = value.getNickname();
                String password = value.getPassword();
                String mobile = value.getMobile();
                String portrait = value.getPortrait();
                Message msg = new Message();
                Log.i("返回data", flat + "::" + message + "::" + userid + "::" + nickname + "::" + password + "::" + mobile + "::" + portrait);
                if (flat.equals("success")) {
                    Log.i("Status", "登录成功，3秒后跳转。。。" );
                    msg.what = 1;
                    msg.obj = data;

                }else {
                    msg.what = -1;
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

    private boolean isMobile(String s) {
        int len = s.length();
        if (len != 11) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                flag = false;
                break;
            }
        }
        if (flag)
            return true;
        else return false;
    }
}
