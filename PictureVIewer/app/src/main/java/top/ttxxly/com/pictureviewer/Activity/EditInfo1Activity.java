package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/***
 * 修改 nickname
 * 修改 手机号
 */


public class EditInfo1Activity extends AppCompatActivity {

    final int EDIT_NICKNAME_CODE = 2;    //修改nickname
    final int EDIT_MOBILE_CODE = 3;    //修改 mobile
    final int NO_RESULT_CODE = 0;       //直接返回不做任何的修改
    private String Url = "http://10.0.2.2/picture_viewer";
    private String nickname = "";
    private String password = "";
    private String mobile = "";
    private String portrait = "";


    private EditText mContent;
    private int code;

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
                    SharedPreferenceUtils.putString("UserPortrait", value.getPortrait(), getApplicationContext());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("content", mContent.getText().toString());
                    intent.putExtras(bundle);
                    setResult(code, intent);
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
        setContentView(R.layout.activity_edit_info1);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        code = extras.getInt("code");
        Log.i("name", name);
        TextView mName = (TextView) findViewById(R.id.tv_edit_info1_name);
        mContent = (EditText) findViewById(R.id.et_edit_info1_content);
        ImageView mReturn = (ImageView) findViewById(R.id.img_edit_info1_return_top);
        Button mConfirm = (Button) findViewById(R.id.btn_edit_info1_confirm);

        //设置返回，当数据为空时
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(NO_RESULT_CODE);
                finish();
            }
        });

        //设置页面描述
        mName.setText(name);

        //确定返回数据的点击事件
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if ( init() ) {
                   StartRequestFromPHP();
               }
            }
        });


    }

    /**
     * 根据返回的 Code 判断修改密码、用户名、还是手机号
     */
    private boolean init() {
        boolean flag = true;

        switch (code) {
            case EDIT_NICKNAME_CODE:
                if (!SharedPreferenceUtils.getString("UserNickname", "", getApplicationContext()).equals(mContent.getText().toString())) {
                    nickname = mContent.getText().toString();
                } else {
                    Toast.makeText(getApplicationContext(), "修改的昵称不能与原昵称相同", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
                break;
            case EDIT_MOBILE_CODE:
                if (!SharedPreferenceUtils.getString("UserMobile", "", getApplicationContext()).equals(mContent.getText().toString())) {
                    mobile = mContent.getText().toString();
                } else {
                    Toast.makeText(getApplicationContext(), "修改的手机号不能与原手机号相同", Toast.LENGTH_SHORT).show();
                    flag = false;
                }
                break;
        }
        return flag;
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
            String url = Url + "/interface/update_user.php" + "?userid=" + userid + "&nickname=" + nickname + "&mobile=" + mobile + "&password=" + password + "&portrait=" + portrait;
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
