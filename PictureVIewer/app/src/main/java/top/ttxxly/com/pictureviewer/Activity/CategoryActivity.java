package top.ttxxly.com.pictureviewer.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import top.ttxxly.com.pictureviewer.Adapter.GlideAdapter;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.Photos;
import top.ttxxly.com.pictureviewer.models.User;

public class CategoryActivity extends AppCompatActivity {

    private String Url = "http://10.0.2.2/picture_viewer";
    private static ProgressDialog pd;// 等待进度圈
    private List<Photos.PhotosBean> photos = new ArrayList<Photos.PhotosBean>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Photos data = new Gson().fromJson(msg.obj.toString(), Photos.class);
            photos = data.getPhotos();
            pd.dismiss();

            switch (msg.what) {
                case 1:
                    gv_category_activity.setAdapter(new GlideAdapter(photos));
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private GridView gv_category_activity;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle extras = getIntent().getExtras();
        title = extras.getString("title");

        gv_category_activity = (GridView) findViewById(R.id.GV_category_activity);

        StartRequestFromPHP();
        gv_category_activity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "点击了"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DetailsPhotosActivity.class);
                intent.putExtra("photos_data", photos.get(position));
                startActivity(intent);
            }
        });
    }

    private void StartRequestFromPHP() {

        pd = ProgressDialog.show(CategoryActivity.this, null, "请稍候...");
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
        try {
            // 创建一个URL对象
            String url = Url + "/interface/selectpic.php" + "?userid=" + SharedPreferenceUtils.getString("UserId", "", getApplicationContext()) + "&keywords=" + title;
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
                    Log.i("Status", "登录成功，3秒后跳转。。。");
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
