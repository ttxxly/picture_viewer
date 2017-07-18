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

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import top.ttxxly.com.pictureviewer.Activity.CategoryActivity;
import top.ttxxly.com.pictureviewer.Adapter.Category_GlideAdapter;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;
import top.ttxxly.com.pictureviewer.Utils.StreamUtils;
import top.ttxxly.com.pictureviewer.models.Category;

import static top.ttxxly.com.pictureviewer.Activity.MainActivity.mContext;

public class CategoryFragment extends Fragment {

    private String Url = "http://10.0.2.2/picture_viewer";
    private static ProgressDialog pd;// 等待进度圈
    private List<String> titles = new ArrayList<String>();
    private GridView gv_category;
    private boolean flag = false;       //标记是否已经获取到数据
    private String[] urls = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1500215893018&di=87f7bf560976ed67d6cd007317572138&imgtype=0&src=http%3A%2F%2Fimgq.duitang.com%2Fuploads%2Fitem%2F201207%2F23%2F20120723120635_8wHRx.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1500205983&di=4e4c6bd43d96ac6a3f22e2abc5b80ad2&src=http://img2.3lian.com/2014/c7/28/d/14.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1500205983&di=b61f88e3af6a3adc14db0f4a9a3e5364&src=http://img4.duitang.com/uploads/item/201602/04/20160204172539_KPmfy.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1500205983&di=5a3c17504839e908b9aa9b934640707e&src=http://uploadfile.bizhizu.cn/2014/0915/20140915101344662.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1500205983&di=8bbe243d0f84941bae66e620faa95427&src=http://img5.duitang.com/uploads/item/201411/14/20141114233609_8PPYd.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1500216598509&di=642215da90c7681c830c4ac2bb72203c&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F97%2F91%2F82758PIC9pG_1024.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1500205983&di=d3bb976283b96391e730850e11486ce8&src=http://www.new-bamboo.com.cn/timage/41824229066/23852546702.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1500205983&di=5659d7561e2e8b3cd8f0e44b01896690&src=http://pic1.win4000.com/wallpaper/6/54508b9eda172.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1500216370925&di=cbf7eff6764a6df62a878fe115ef746e&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F48%2F32%2F33y58PICvwD_1024.jpg"
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    gv_category.setAdapter(new Category_GlideAdapter(titles, urls));
                    gv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(mContext, "点击事件", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, CategoryActivity.class);
                            intent.putExtra("title", titles.get(position));
                            startActivity(intent);
                        }
                    });
                    break;
                case -1:
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("分类", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        gv_category = (GridView) view.findViewById(R.id.GV_category);
        SendRequestToCategory();
        return view;
    }

    private void SendRequestToCategory() {

        //pd = ProgressDialog.show(mContext, null, "分类数据加载中，请稍候...");
        // String userid = SharedPreferenceUtils.getString("UserId", "", getContext());
        new Thread() {
            @Override
            public void run() {
                super.run();
                String userid = SharedPreferenceUtils.getString("UserId", "", mContext);
                HttpURLConnection conn = null;
                try {
                    // 创建一个URL对象
                    String url = Url + "/interface/category.php" + "?userid=" + userid;
                    Log.i("分类URl", url);
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
                        Log.i("分类data", data);
                        Category value = new Gson().fromJson(data, Category.class);
                        //pd.dismiss();
                        String flat = value.getFlat();
                        Message message = new Message();
                        if (flat.equals("success")) {
                            Log.i("分类Status", "分类数据请求成功！！！");
                            flag = true;
                            titles = value.getArra();
                            message.what = 1;
                        } else {
                            Log.i("分类Status", "分类数据请求失败！！！");
                            flag = false;
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
