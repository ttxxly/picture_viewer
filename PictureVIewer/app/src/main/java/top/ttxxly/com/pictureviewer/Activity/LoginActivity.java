package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import top.ttxxly.com.pictureviewer.Abstract.User;
import top.ttxxly.com.pictureviewer.Abstract.parse;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText et_nickname;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "点击了登录按钮", Toast.LENGTH_SHORT).show();
                StartRequestFromPHP();
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
                    SendRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        if (SharedPreferenceUtils.getBoolean("loginInfo", true, this)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else {
            Toast.makeText(getApplicationContext(), "用户名或者密码错误。。", Toast.LENGTH_SHORT).show();
        }

    }

    private void SendRequest() {
        User user = new User();
        user.init();
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
            String url = "http://10.0.2.2/picture_viewer/login.php" + "?nickname=" + user.getNickname() + "&password=" + user.getPassword() + "&mobile=" + user.getMobile();
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
                parse value = new Gson().fromJson(data, parse.class);
                String flat = value.getFlat();
                String message = value.getMessage();
                String nickname = value.getNickname();
                String password = value.getPassword();
                String mobile = value.getMobile();
                String portrait = value.getPortrait();
                Log.i("返回data", flat + "::" + message + "::" + nickname + "::" + password + "::" + mobile + "::" + portrait);
                if (flat.equals("success")) {
                    SharedPreferenceUtils.putBoolean("loginInfo", true, this);
                    SharedPreferenceUtils.putString("loginNickname", nickname, this);
                    SharedPreferenceUtils.putString("loginPassword", password, this);
                    SharedPreferenceUtils.putString("loginMobile", mobile, this);
                    SharedPreferenceUtils.putString("loginPortrait", portrait, this);
                    Toast.makeText(getApplicationContext(), "登录成功，3秒后跳转。。。", Toast.LENGTH_SHORT).show();
                }

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
