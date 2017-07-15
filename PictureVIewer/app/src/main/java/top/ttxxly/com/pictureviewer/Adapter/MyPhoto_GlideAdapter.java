package top.ttxxly.com.pictureviewer.Adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.models.Photos;

/**
 * Created by ttxxly on 2017/7/1.
 */

public class MyPhoto_GlideAdapter extends BaseAdapter {

    private String Url = "http://10.0.2.2/picture_viewer";

    List<Photos.PhotosBean> photosBeanList;
    public MyPhoto_GlideAdapter(List<Photos.PhotosBean> photosBeanList) {
        this.photosBeanList = photosBeanList;
        Log.i("图片GlideAdapter数量", ""+photosBeanList.size());
    }

    @Override
    public int getCount() {
        return photosBeanList.size();
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

        View v = View.inflate(parent.getContext(), R.layout.gridview_item, null);   //获取视图对象

        //获取相应的布局
        ImageView grideView_image = (ImageView) v.findViewById(R.id.grideView_image);

        //grideView_title.setText(title[position]);
        /*
        * .override(300, 300) //设置显示图片大小
        * .diskCacheStrategy(DiskCacheStrategy.NONE) //禁止缓存
        * .placeholder(R.mipmap.girls) //占位图，图片还没有加载完成的时候就显示这张图片
        * .centerCrop()     //缩放图像让它填充到 ImageView 界限内并且裁剪额外的部分
        * */
        Log.i("图片glideAdapter_url", Url + photosBeanList.get(position).getUrl());
        Glide.with(parent.getContext()).load(Url + photosBeanList.get(position).getUrl()).placeholder(R.mipmap.login_pic).centerCrop().into(grideView_image);
        return v;
    }
}
