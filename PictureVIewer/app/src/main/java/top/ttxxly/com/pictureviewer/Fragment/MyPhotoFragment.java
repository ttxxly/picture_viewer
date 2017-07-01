package top.ttxxly.com.pictureviewer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import top.ttxxly.com.pictureviewer.Adapter.GlideAdapter;
import top.ttxxly.com.pictureviewer.R;


public class MyPhotoFragment extends Fragment {

    public static String[] imageSources = {
            "http://img15.3lian.com/2016/h1/124/171.jpg",
            "http://scimg.jb51.net/allimg/150819/14-150QZ9194K27.jpg",
            "http://pic.58pic.com/58pic/16/00/52/11J58PICD3S_1024.jpg",
            "http://pic19.huitu.com/res/20140427/33714_20140427005724677341_1.jpg",
            "http://pic19.huitu.com/res/20140427/33714_20140427005724677341_1.jpg",
            "http://pic15.photophoto.cn/20100615/0006019058815826_b.jpg",
            "http://pic33.photophoto.cn/20141204/0006019060393096_b.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba61e5e6d4c0d4b34970a304e5915.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/574e9258d109b3de25b6324bc6bf6c81800a4c8c.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/5366d0160924ab18cb0aad123ffae6cd7b890bf2.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/0dd7912397dda144ccc45891b8b7d0a20df486c7.jpg",
            "http://www.chla.com.cn/uploadfile/2011/new2011617/images/2013/10/20131011164609.jpg",
            "http://tupian.enterdesk.com/2013/mxy/12/28/1/3.jpg",
            "http://pic.58pic.com/58pic/17/14/25/30X58PICbYW_1024.jpg"
    };
    public static String[] title = {
            "vfdgdf",
            "vfdf",
            "vergdf",
            "vereredf",
            "vfwerewte",
            "vfddgd",
            "vdddddd",
            "vftttt",
            "vfdbbbbb",
            "vfdgddddd",
            "vfdg3333",
            "vfdgddddd",
            "vfdg3333",
            "vfdgddddd"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_photo, container, false);

        GridView gv_my_photo = (GridView) view.findViewById(R.id.gv_my_photo);
        gv_my_photo.setAdapter(new GlideAdapter(imageSources));
        /*gv_my_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        // Inflate the layout for this fragment
        return view;

    }

}
