package top.ttxxly.com.pictureviewer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import top.ttxxly.com.pictureviewer.R;


public class MyPhotoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_photo, container, false);
        ImageView mMyPhoto = (ImageView) view.findViewById(R.id.img_my_photo);
        ImageView mMyPhoto2 = (ImageView) view.findViewById(R.id.img_my_photo2);
        ImageView mMyPhoto3 = (ImageView) view.findViewById(R.id.img_my_photo3);
        ImageView mMyPhoto4 = (ImageView) view.findViewById(R.id.img_my_photo4);
        ImageView mMyPhoto5 = (ImageView) view.findViewById(R.id.img_my_photo5);
        ImageView mMyPhoto6 = (ImageView) view.findViewById(R.id.img_my_photo6);

        String internetUrl1 = "http://android-artworks.25pp.com/fs08/2016/06/07/10/1_35b0c5cda3e1051acb9068b0c79d6fe9_con.png";
        String internetUrl2 = "http://img2.imgtn.bdimg.com/it/u=1012912279,3382694267&fm=26&gp=0.jpg";
        String internetUrl3 = "http://img4.duitang.com/uploads/item/201408/30/20140830185456_Eijik.jpeg";
        String internetUrl4 = "http://img2.imgtn.bdimg.com/it/u=2316535535,872567517&fm=214&gp=0.jpg";
        String internetUrl5 = "http://img0.ph.126.net/XjXl3KcowmXdE1pcsFVe8g==/1067353111787095545.jpg";
        String internetUrl6 = "http://pic38.nipic.com/20140303/18070641_203310278152_2.jpg";
        Glide.with(this).load(internetUrl1).into(mMyPhoto);
        Glide.with(this).load(internetUrl2).into(mMyPhoto2);
        Glide.with(this).load(internetUrl3).into(mMyPhoto3);
        Glide.with(this).load(internetUrl4).into(mMyPhoto4);
        Glide.with(this).load(internetUrl5).into(mMyPhoto5);
        Glide.with(this).load(internetUrl6).into(mMyPhoto6);

        // Inflate the layout for this fragment
        return view;

    }

}
