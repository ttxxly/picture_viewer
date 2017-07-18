package top.ttxxly.com.pictureviewer.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.Category;
import top.ttxxly.com.pictureviewer.models.Photos;

public class EditPicActivity extends AppCompatActivity {

    private String Url = "http://10.0.2.2/picture_viewer";
    private TextView edit_pic_title;
    private TextView edit_pic_keywords;
    private TextView edit_pic_description;
    private ImageView edit_pic_picture;
    private ImageView edit_pic_return;
    private Button edit_pic_submit;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "修改图片信息成功!!!!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "修改图片信息失败!!!!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private Photos.PhotosBean photos;
    private TextView edit_pic_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pic);

        photos = (Photos.PhotosBean) getIntent().getSerializableExtra("photos_data");

        edit_pic_title = (TextView) findViewById(R.id.et_edit_pic_title);
        edit_pic_title.setText(photos.getTitle());
        edit_pic_keywords = (TextView) findViewById(R.id.et_edit_pic_keywords);
        edit_pic_keywords.setText(photos.getKeyword());
        edit_pic_category = (TextView) findViewById(R.id.et_edit_pic_category);
        edit_pic_category.setText(photos.getCategoryid());
        edit_pic_description = (TextView) findViewById(R.id.et_edit_pic_description);
        edit_pic_description.setText(photos.getDescription());
        edit_pic_picture = (ImageView) findViewById(R.id.img_edit_pic_picture);
        Glide.with(getApplicationContext()).load(Url + photos.getUrl()).placeholder(R.mipmap.login_pic).centerCrop().into(edit_pic_picture);
        edit_pic_return = (ImageView) findViewById(R.id.img_edit_pic_return_top);
        edit_pic_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit_pic_submit = (Button) findViewById(R.id.btn_edit_pic_submit);
        edit_pic_submit.setOnClickListener(new View.OnClickListener() {

            private String description;
            private String keywords;
            private String title;

            @Override
            public void onClick(View v) {
                title = edit_pic_title.getText().toString();
                keywords = edit_pic_keywords.getText().toString();
                description = edit_pic_description.getText().toString();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(keywords) && !TextUtils.isEmpty(description)) {
                    SendRequestToEditPic();
                }else
                    Toast.makeText(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SendRequestToEditPic() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpURLConnection conn = null;
                try {
                    // 创建一个URL对象
                    String userid = SharedPreferenceUtils.getString("UserId", "", getApplicationContext());
                    String url = Url + "/interface/edit_picInfo.php" + "?id="+photos.getPhotoid()+"&title="+ Uri.encode(edit_pic_title.getText().toString())+"&keyword="+Uri.encode(edit_pic_keywords.getText().toString()) +"&category="+Uri.encode(edit_pic_category.getText().toString())+"&description="+Uri.encode(edit_pic_description.getText().toString())+"&userid="+userid;
                    Log.i("9999999URl", url+"0999999999999999");
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
                        Log.i("修改图片信息", data);
                        Category value = new Gson().fromJson(data, Category.class);
                        //pd.dismiss();
                        String flat = value.getFlat();
                        Message message = new Message();
                        if (flat.equals("success")) {
                            Log.i("修改图片信息Status", "图片信息修改成功！！！");
                            message.what = 1;
                        } else {
                            Log.i("修改图片信息Status", "图片信息修改失败！！！");
                            message.what = -1;
                        }
                        handler.sendMessage(message);
                    } else {
                        Log.i("分类访问失败", "responseCode");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("分类访问失败", "无法连接服务器");
                } finally {
                    if (conn != null) {
                        conn.disconnect();// 关闭连接
                    }
                }
            }
        }.start();
    }
}
