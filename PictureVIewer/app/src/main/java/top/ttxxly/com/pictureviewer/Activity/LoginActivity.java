package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private String Url = "http://10.0.2.2/picture_viewer";
    private ImageView mShowPwd;
    private ImageView mDeleteNickname;
    private TextView mLook;

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
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setEventListener();

    }

    /**
     * 初始化该页面各个组件
     */
    private void init() {

        et_nickname = (EditText) findViewById(R.id.et_login_nickname);
        mDeleteNickname = (ImageView) findViewById(R.id.img_login_delete_nickname);
        mDeleteNickname.setVisibility(View.GONE);   //设置图片隐藏
        mShowPwd = (ImageView) findViewById(R.id.img_login_show_pwd);
        mShowPwd.setVisibility(View.GONE);//设置图片隐藏
        btn_login = (Button) findViewById(R.id.btn_login);
        et_password = (EditText) findViewById(R.id.et_login_password);
        mLook = (TextView) findViewById(R.id.tv_login_look_around);
        tv_register = (TextView) findViewById(R.id.tv_register);

    }

    /**
     * 为该页面各组件设置事件侦听
     */
    private void setEventListener() {

        et_nickname.addTextChangedListener(new TextWatcher() {  //监听输入的内容

            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            /**
             * 在原有的文本s中，从start开始的count个字符将会被一个新的长度为after的文本替换，
             * 注意这里是将被替换，还没有被替换。原文本 s 不能被修改
             * @param s 原来的文本
             * @param start 即将改变的开始位置
             * @param count 将要改变的字符数
             * @param after 新文本的长度
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("beforeTextChanged", "s="+s+" start="+start + " count="+count+" after="+after);
                temp = s;
            }


            /**
             * 在原有的文本s中，从start开始的count个字符替换长度为before的旧文本，执行了替换动作。
             * 原文本 s 不能被修改
             * @param s     原文本
             * @param start 将被替换的开始位置
             * @param before    原文本中被替换的长度
             * @param count     从start开始将被替换的字符数
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("onTextChanged", "s="+s+" start="+start + " count="+count+" before="+before);
            }

            /**
             * 在EditText内容已经改变之后调用
             * @param s
             */
            @Override
            public void afterTextChanged(Editable s) {
                Log.i("afterTextChanged", "内容已经改变");
                selectionStart = et_nickname.getSelectionStart();   //光标的偏移量
                selectionEnd = et_nickname.getSelectionEnd();
                if (temp.length() > 20) {
                    Toast.makeText(LoginActivity.this, "亲，你的名字实在是太长了", Toast.LENGTH_SHORT).show();
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    et_nickname.setText(s);
                    et_nickname.setSelection(tempSelection);
                }
                if(temp.length() > 0)
                    mDeleteNickname.setVisibility(View.VISIBLE);
                else
                    mDeleteNickname.setVisibility(View.GONE);
            }
        });


        et_password.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selectionStart = et_password.getSelectionStart();   //光标的偏移量
                selectionEnd = et_password.getSelectionEnd();
                if (temp.length() > 10) {
                    Toast.makeText(LoginActivity.this, "亲，你的密码还是不要太复杂吧", Toast.LENGTH_SHORT).show();
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    et_password.setText(s);
                    et_password.setSelection(tempSelection);
                }
                if(temp.length() > 0)
                    mShowPwd.setVisibility(View.VISIBLE);
                else
                    mShowPwd.setVisibility(View.GONE);
            }
        });


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

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        mDeleteNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_nickname.setText("");
            }
        });
        mShowPwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //隐藏密码
                //et_password.setTransformationMethod((PasswordTransformationMethod.getInstance()));
                //显示密码
               // et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://按住
                        Log.i("按住", "按住");
                        et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        break;
                    case MotionEvent.ACTION_UP: //松开
                        Log.i("松开", "松开");
                        et_password.setTransformationMethod((PasswordTransformationMethod.getInstance()));
                        break;
                }
                return true;
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
            String url = Url + "/interface/login.php" + "?nickname=" + user.getNickname() + "&password=" + user.getPassword() + "&mobile=" + user.getMobile();
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
            Log.i("访问失败1", "无法连接服务器");
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
