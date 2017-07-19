package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.Photos;
import top.ttxxly.com.pictureviewer.models.User;

public class DetailsPhotosActivity extends AppCompatActivity {

    private TextView title;
    private TextView category;
    private TextView keyword;
    private TextView date;
    private TextView description;
    private ImageView pic;
    private String Url = "http://10.0.2.2/picture_viewer";
    private ImageView mReturn;
    private ImageView mEdit;
    private Photos.PhotosBean photos;
    private ImageView mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_photos);

        photos = (Photos.PhotosBean) getIntent().getSerializableExtra("photos_data");

        title = (TextView) findViewById(R.id.tv_details_photos_title);
        date = (TextView) findViewById(R.id.tv_details_photos_date);
        description = (TextView) findViewById(R.id.tv_details_photos_description);
        pic = (ImageView) findViewById(R.id.img_details_pic);
        mReturn = (ImageView) findViewById(R.id.img_details_return_top);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEdit = (ImageView) findViewById(R.id.img_details_edit);
        mDelete = (ImageView) findViewById(R.id.img_details_delete);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartRequestFromPHP();
            }
        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsPhotosActivity.this, EditPicActivity.class);
                intent.putExtra("photos_data", photos);
                startActivityForResult(intent, 1);
            }
        });
        Log.i("Detail_url", Url+ photos.getUrl());
        Glide.with(getApplicationContext()).load(Url+ photos.getUrl()).placeholder(R.mipmap.login_pic).centerCrop().into(pic);
        date.setText(photos.getDatetime());
        description.setText(photos.getDescription());
        title.setText(photos.getTitle());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                break;
        }
    }

    private void StartRequestFromPHP() {

        //新建线程
        new Thread() {
            public void run() {
                try {
                    HttpURLConnection conn = null;
                    try {
                        // 创建一个URL对象
                        String url = Url + "/interface/deletePicture.php" + "?id="+ Uri.encode(photos.getPhotoid()) ;
                        Log.i("URl", url);
                        URL mURL = new URL(url);
                        // 调用URL的openConnection()方法,获取HttpURLConnection对象
                        conn = (HttpURLConnection) mURL.openConnection();
                        conn.setRequestMethod("GET");// 设置请求方法为get
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
                                Log.i("Status", "删除成功！！！" );
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
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data = msg.obj.toString();
            User value = new Gson().fromJson(data, User.class);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), value.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), value.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
