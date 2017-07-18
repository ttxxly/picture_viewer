package top.ttxxly.com.pictureviewer.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import top.ttxxly.com.pictureviewer.Activity.DetailsPhotosActivity;
import top.ttxxly.com.pictureviewer.Adapter.MyPhoto_GlideAdapter;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.Photos;

import static top.ttxxly.com.pictureviewer.Activity.MainActivity.mContext;


public class MyPhotoFragment extends Fragment {

    private String Url = "http://10.0.2.2/picture_viewer";
    private static ProgressDialog pd;// 等待进度圈
    private List<Photos.PhotosBean> photos = new ArrayList<Photos.PhotosBean>();
    private GridView gv_my_photo;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    gv_my_photo.setAdapter(new MyPhoto_GlideAdapter(photos));
                    gv_my_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(mContext, "点击事件", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, DetailsPhotosActivity.class);
                            intent.putExtra("photos_data", photos.get(position));
                            startActivity(intent);
                        }
                    });
                    break;
                case -1:
                    Toast.makeText(mContext, "数据获取失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("图片", "onCreateView()被调用");
        View view = inflater.inflate(R.layout.fragment_my_photo, container, false);
        gv_my_photo = (GridView) view.findViewById(R.id.gv_my_photo);
        SendRequestToPhotos();
        return view;
    }

    private void SendRequestToPhotos() {

        //pd = ProgressDialog.show(mContext, null, "图片数据加载中，请稍候...");
        new Thread() {
            @Override
            public void run() {
                super.run();
                String mUserId = SharedPreferenceUtils.getString("UserId", "", mContext);
                String keys = "";
                HttpURLConnection conn = null;
                try {
                    // 创建一个URL对象
                    String url = Url + "/interface/selectpic.php" + "?userid=" + mUserId + "&keys=" + keys;
                    Log.i("图片URl", url);
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
                        Log.i("图片data", data);
                        Photos value = new Gson().fromJson(data, Photos.class);
                        //pd.dismiss();
                        String flat = value.getFlat();
                        Message message = new Message();
                        if (flat.equals("success")) {
                            Log.i("图片请求Status", "请求我的图片成功！！！");
                            photos = value.getPhotos();
                            message.what = 1;
                        } else {
                            Log.i("图片请求Status", "请求我的图片失败！！！");
                            message.what = -1;
                        }
                        handler.sendMessage(message);
                    } else {
                        Log.i("图片数据访问失败", "responseCode");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("图片数据访问失败", "无法连接服务器");
                } finally {
                    if (conn != null) {
                        conn.disconnect();// 关闭连接
                    }
                }
            }
        }.start();
    }


}
