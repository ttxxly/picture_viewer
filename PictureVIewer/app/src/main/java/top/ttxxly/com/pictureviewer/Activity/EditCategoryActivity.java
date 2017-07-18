package top.ttxxly.com.pictureviewer.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.User;

public class EditCategoryActivity extends AppCompatActivity {

    private String Url = "http://10.0.2.2/picture_viewer";
    private EditText mEditCategoryTitle;
    private EditText mEitCategoryKeywords;
    private EditText mEitCategoryDescription;
    private Button mEditCategoryConfirm;
    private ImageView mReturn;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "修改分类成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "修改分类失败，请返回", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        mEditCategoryTitle = (EditText) findViewById(R.id.et_edit_category_title);
        mEitCategoryKeywords = (EditText) findViewById(R.id.et_edit_category_keywords);
        mEitCategoryDescription = (EditText) findViewById(R.id.et_edit_category_description);
        mEditCategoryConfirm = (Button) findViewById(R.id.btn_edit_category_confirm);
        mReturn = (ImageView) findViewById(R.id.img_edit_category_return_top);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditCategoryConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartRequestFromPHP();
            }
        });
    }
    private void StartRequestFromPHP() {

        //新建线程
        new Thread() {
            public void run() {
                try {
                    HttpURLConnection conn = null;
                    try {
                        // 创建一个URL对象
                        String url = Url + "/interface/update_category.php" ;
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
