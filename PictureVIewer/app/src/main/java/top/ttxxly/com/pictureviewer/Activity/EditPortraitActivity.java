package top.ttxxly.com.pictureviewer.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.ttxxly.com.pictureviewer.Adapter.Home_GlideAdapter;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.FileUtil;
import top.ttxxly.com.pictureviewer.Utils.NetUtil;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.Photos;
import top.ttxxly.com.pictureviewer.models.User;

import static top.ttxxly.com.pictureviewer.Activity.MainActivity.mContext;

public class EditPortraitActivity extends AppCompatActivity {
    final int EDIT_PORTRAIT_CODE = 1;    //修改 portrait
    private String Url = "http://10.0.2.2/picture_viewer";
    private String str;
    private String userid;
    private static ProgressDialog pd;// 等待进度圈
    private static final int REQUESTCODE_PICK = 4;		// 相册选图标记
    private List<Photos.PhotosBean> photos = new ArrayList<Photos.PhotosBean>();
    private String nickname = "";
    private String password = "";
    private String mobile = "";
    private String portrait = "";
    private int Position = 0;//点击的位置
    private String imgUrl = "http://10.0.2.2/picture_viewer/interface/upload_pictures.php";
    private GridView mPortrait;
    private ImageView img_edit_portrait_upload;
    private String urlpath;
    private Uri address;
    private Bitmap p;
    private String filename;
    private int code;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            code = msg.what;
            switch (msg.what) {
                case -1:
                    Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Photos data = new Gson().fromJson(msg.obj.toString(), Photos.class);
                    photos = data.getPhotos();
                    mPortrait.setAdapter(new Home_GlideAdapter(photos));
                    Toast.makeText(getApplicationContext(), "头像请求成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    User User_data = new Gson().fromJson(msg.obj.toString(), User.class);
                    Toast.makeText(getApplicationContext(), User_data.getMessage(), Toast.LENGTH_SHORT).show();
                    SharedPreferenceUtils.putString("UserNickname", User_data.getNickname(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserPassword", User_data.getPassword(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserMobile", User_data.getMobile(), getApplicationContext());
                    SharedPreferenceUtils.putString("UserPortrait", User_data.getPortrait(), getApplicationContext());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", photos.get(Position).getUrl());
                    intent.putExtras(bundle);
                    setResult(EDIT_PORTRAIT_CODE, intent);
                    finish();
                    break;
                case 3:
                    portrait = "/photos/photos/" + filename;
                    StartRequestFromPHP1();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_portrait);

        StartRequestFromPHP();
        ImageView mReturn = (ImageView) findViewById(R.id.img_edit_portrait_return_top);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_edit_portrait_upload = (ImageView) findViewById(R.id.img_edit_portrait_upload);
        img_edit_portrait_upload.setOnClickListener(new View.OnClickListener() {//上传头像的侦听事件
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, REQUESTCODE_PICK);
            }
        });
        mPortrait = (GridView) findViewById(R.id.GV_edit_portrait);
        mPortrait.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                portrait = photos.get(position).getUrl();
                Position = position;
                StartRequestFromPHP1();
            }
        });
    }

    /***
     * 显示九宫格头像
     */
    private void StartRequestFromPHP() {

        //新建线程
        new Thread() {
            public void run() {
                try {
                    String keys = "portrait";
                    HttpURLConnection conn = null;
                    try {
                        // 创建一个URL对象
                        String url = Url + "/interface/selectpic.php" + "?userid=" + "" + "&keys=" + keys;
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
                            Photos value = new Gson().fromJson(data, Photos.class);

                            String flat = value.getFlat();
                            Message msg = new Message();
                            if (flat.equals("success")) {
                                Log.i("Status", "修改用户信息请求成功！！！");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /***
     * 更新用户头像信息
     */
    private void StartRequestFromPHP1() {

        //新建线程
        new Thread() {
            public void run() {
                try {
                    String mUserId = SharedPreferenceUtils.getString("UserId", "", getApplicationContext());
                    HttpURLConnection conn = null;
                    try {
                        // 创建一个URL对象
                        String url = Url + "/interface/update_user.php" + "?userid=" + mUserId + "&nickname=" + nickname + "&mobile=" + mobile + "&password=" + password + "&portrait=" + portrait;
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
                                Log.i("Status", "修改用户信息请求成功！！！");
                                msg.what = 2;
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return ;
        }
        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    urlpath = data.getData().toString();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(urlpath)) {
                    Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                    intent.putExtra("url", urlpath);
                    Log.i("2222222221urlPath", urlpath);
                    try {
                        address = data.getData();
                        p = MediaStore.Images.Media.getBitmap(getContentResolver(), address);
                        if(p != null) {
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                            filename = df.format(new Date())+".jpg";
                            urlpath = FileUtil.saveFile(EditPortraitActivity.this, filename, p);
                            Log.i("图片名称123123", df.format(new Date())+".jpg");
                            Log.i("图片路径123123", urlpath);
                            // 新线程后台上传服务端
                            pd = ProgressDialog.show(mContext, null, "正在上传图片，请稍候...");
                            Log.i("11111111111111urlPath", urlpath);
                            new Thread(uploadImageRunnable).start();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    Runnable uploadImageRunnable = new Runnable() {


        private HttpURLConnection conn;

        @Override
        public void run() {

            userid = SharedPreferenceUtils.getString("UserId", "",getApplicationContext());
            if(TextUtils.isEmpty(imgUrl)){
                Log.i("上传错误", "还没有设置上传服务器的路径！");
                return;
            }

            Map<String, String> textParams = new HashMap<String, String>();
            Map<String, File> fileparams = new HashMap<String, File>();

            try {
                // 创建一个URL对象
                URL url = new URL(imgUrl);
                textParams = new HashMap<String, String>();
                fileparams = new HashMap<String, File>();
                // 要上传的图片文件

                File file = new File(urlpath);
                if(file.exists()) {
                    Log.i("1010101", "101010101010101001");
                }
                fileparams.put("myfile", file);
                textParams.put("title", "portrait");
                textParams.put("keywords", "portrait");
                textParams.put("description", "portrait");
                textParams.put("userid", userid);

                // 利用HttpURLConnection对象从网络中获取网页数据
                conn = (HttpURLConnection) url.openConnection();
                // 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
                conn.setConnectTimeout(5000);
                // 设置允许输出（发送POST请求必须设置允许输出）
                conn.setDoOutput(true);
                // 设置使用POST的方式发送
                conn.setRequestMethod("POST");
                // 设置不使用缓存（容易出现问题）
                conn.setUseCaches(false);
                conn.setRequestProperty("Charset", "UTF-8");//设置编码
                // 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
                conn.setRequestProperty("ser-Agent", "Fiddler");
                // 设置contentType
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
                OutputStream os = conn.getOutputStream();
                DataOutputStream ds = new DataOutputStream(os);
                NetUtil.writeStringParams(textParams, ds);
                NetUtil.writeFileParams(fileparams, ds);
                NetUtil.paramsEnd(ds);
                // 对文件流操作完,要记得及时关闭
                os.close();
                // 服务器返回的响应吗
                int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
                Message message = new Message();
                // 对响应码进行判断
                if (code == 200) {// 返回的响应码200,是成功
                    // 得到网络返回的输入流
                    InputStream is = conn.getInputStream();
                    str = NetUtil.readString(is);
                    file.delete();  //删除暂存的图片文件
                    message.what = 3;
                } else {
                    Log.i("上传错误", "请求URL失败！");
                    message.what = -3;
                }
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (conn != null) {
                    conn.disconnect();// 关闭连接
                }
                pd.dismiss();
            }
        }
    };

}
