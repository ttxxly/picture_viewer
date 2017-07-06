package top.ttxxly.com.pictureviewer.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.ttxxly.com.pictureviewer.Adapter.RecyclerViewAdapter;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.models.Category;

public class CategoryFragment extends Fragment {

    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        //找到RecyclerView控件
        RecyclerView home_rv = (RecyclerView) v.findViewById(R.id.RCV_category_recyclerView);

        //Category类获取信息
        List dataList = new ArrayList();
        Category category = new Category();
        category.set
        dataList.add();

        //实例化Adapter并且给RecyclerView设上
        adapter = new RecyclerViewAdapter(dataList);
        home_rv.setAdapter(adapter);

        // 如果我们想要一个GridView形式的RecyclerView，那么在LayoutManager上我们就要使用GridLayoutManager
        // 实例化一个GridLayoutManager，列数为3
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);

        //调用以下方法让RecyclerView的第一个条目仅为1列
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果位置是0，那么这个条目将占用SpanCount()这么多的列数，再此也就是3
                //而如果不是0，则说明不是Header，就占用1列即可
                return adapter.isHeader(position) ? layoutManager.getSpanCount() : 1;
            }
        });

        //把LayoutManager设置给RecyclerView
        home_rv.setLayoutManager(layoutManager);
        return v;


    }

}
