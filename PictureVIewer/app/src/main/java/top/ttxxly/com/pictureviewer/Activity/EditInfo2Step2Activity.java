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

import static top.ttxxly.com.pictureviewer.Activity.MainActivity.mContext;

public class EditInfo2Step2Activity extends AppCompatActivity {

    private EditText mNew_password;
    private EditText mNew_password_again;
    private String Url = "http://10.0.2.2/picture_viewer";
    private String nickname = "";
    private String password = "";
    private String mobile = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data = msg.obj.toString();
            User value = new Gson().fromJson(data, User.class);
            switch (msg.what) {
                case 1:
                    Log.i("S", data);
                    Toast.makeText(getApplicationContext(), value.getMessage(), Toast.LENGTH_SHORT).show();
                    SharedPreferenceUtils.putString("UserNickname", value.getNickname(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserPassword", value.getPassword(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserMobile", value.getMobile(), getApplicationContext());
                    SharedPreferenceUtils.putBoolean("UserStatus", false, getApplicationContext());
                    mContext.finish();
                    Intent intent = new Intent();
                    intent.setClass(EditInfo2Step2Activity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
                    startActivity(intent);
                    finish();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), value.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info2_step2);

        mNew_password = (EditText) findViewById(R.id.et_edit_info2_new_password);
        mNew_password_again = (EditText) findViewById(R.id.et_edit_info2_new_password_again);
        ImageView mReturn = (ImageView) findViewById(R.id.img_edit_info2_step2_return_top);
        Button mConfirm = (Button) findViewById(R.id.img_edit_info2_confirm);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditInfo2Activity.class));
                finish();
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mPassword = mNew_password.getText().toString();
                String mPasswordAgain = mNew_password_again.getText().toString();

                if(TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(getApplicationContext(), "输入密码不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(mPasswordAgain)) {
                    Toast.makeText(getApplicationContext(), "再次输入密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    if (mPassword.equals(mPasswordAgain)) {
                        password = mNew_password.getText().toString();
                        //密码格式正确， 可以确定修改了
                        /**
                         * 1. 远程数据库修改，
                         * 2. 数据库修改后，本地 SharedPreferenceUtils.putString("loginPassword")
                         * 3. finish() ，弹出 “修改成功” 吐司对话框
                         * */
                        StartRequestFromPHP();
                    }else {
                        Toast.makeText(getApplicationContext(), "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
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

        HttpURLConnection conn = null;

        String userid = SharedPreferenceUtils.getString("UserId", "", getApplicationContext());

        try {
            // 创建一个URL对象
            String url = Url + "/interface/update_user.php" + "?userid=" + userid + "&nickname=" + nickname + "&mobile=" + mobile + "&password=" + password;
            Log.i("URl", url);
            URL mURL = new URL(url);
            // 调用URL的openConnection()方法,获取HttpURLConnection对象
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET");// 设置请求方法为post
            conn.setReadTimeout(3000);// 设置读取超时为1秒
            conn.setConnectTimeout(3000);// 设置连接网络超时为1秒
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
                    Log.i("Status", "信息修改成功!!!");
                    msg.what = 1;
                    msg.obj = data;
                } else {
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            } else {
                Log.i("访问失败", "responseCode");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("访问失败1", "无法连接服务器");
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }

    }
}
