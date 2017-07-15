package top.ttxxly.com.pictureviewer.Adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.ttxxly.com.pictureviewer.R;

/**
 * Created by ttxxly on 2017/7/1.
 */

public class Category_GlideAdapter extends BaseAdapter {

    private String Url = "http://10.0.2.2/picture_viewer";

    private List<String> titleList;
    private String[] urlList;
    public Category_GlideAdapter(List<String> titleList, String[] urlList) {
        this.titleList = titleList;
        this.urlList = urlList;
        Log.i("分类GlideAdapter数量", ""+titleList.size());
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(parent.getContext(), R.layout.gridview_item1, null);   //获取视图对象

        //获取相应的布局
        ImageView grideView_image = (ImageView) v.findViewById(R.id.grideView_image);
        TextView text = (TextView) v.findViewById(R.id.grideView_text);
        //grideView_title.setText(title[position]);
        /*
        * .override(300, 300) //设置显示图片大小
        * .diskCacheStrategy(DiskCacheStrategy.NONE) //禁止缓存
        * .placeholder(R.mipmap.girls) //占位图，图片还没有加载完成的时候就显示这张图片
        * .centerCrop()     //缩放图像让它填充到 ImageView 界限内并且裁剪额外的部分
        * */
        Log.i("分类GlideAdapter_url", urlList[position]);
        Glide.with(parent.getContext()).load(urlList[position]).placeholder(R.mipmap.login_pic).centerCrop().into(grideView_image);
        text.setText(titleList.get(position));
        return v;
    }
}
