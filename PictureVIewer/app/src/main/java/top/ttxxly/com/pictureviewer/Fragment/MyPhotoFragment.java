package top.ttxxly.com.pictureviewer.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    protected boolean isVisible;    //Fragment当前状态是否可见
    private String Url = "http://10.0.2.2/picture_viewer";
    private static ProgressDialog pd;// 等待进度圈
    private List<Photos.PhotosBean> photos = new ArrayList<Photos.PhotosBean>();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("图片setUserVisibleHint", "图片setUserVisibleHint执行了");
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {

        StartRequestFromPHP();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Photos data = new Gson().fromJson(msg.obj.toString(), Photos.class);
            photos = data.getPhotos();
            pd.dismiss();
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "图片数据请求成功", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(mContext, "图片数据请求失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private GridView gv_my_photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("图片onCreateView", "图片onCreateView执行了");
        View view = inflater.inflate(R.layout.fragment_my_photo, container, false);
        pd = ProgressDialog.show(mContext, null, "图片数据加载中，请稍候...");
        gv_my_photo = (GridView) view.findViewById(R.id.gv_my_photo);
        gv_my_photo.setAdapter(new MyPhoto_GlideAdapter(photos));
        gv_my_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailsPhotosActivity.class);
                intent.putExtra("photos_data", photos.get(position));
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }

    private void StartRequestFromPHP() {

        //新建线程
        new Thread() {
            public void run() {
                try {
                    SendRequestToEditUserInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void SendRequestToEditUserInfo() {

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

                String flat = value.getFlat();
                Message msg = new Message();
                if (flat.equals("success")) {
                    Log.i("图片请求Status", "请求我的图片成功！！！");
                    msg.what = 1;
                    msg.obj = data;
                } else {
                    msg.what = -1;
                }
                handler.sendMessage(msg);
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


}
