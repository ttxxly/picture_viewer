package top.ttxxly.com.pictureviewer.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import top.ttxxly.com.pictureviewer.Adapter.ViewPagerAdapter;
import top.ttxxly.com.pictureviewer.Fragment.ClassificationFragment;
import top.ttxxly.com.pictureviewer.Fragment.HomeFragment;
import top.ttxxly.com.pictureviewer.Fragment.MyPhotoFragment;
import top.ttxxly.com.pictureviewer.Fragment.PeopleFragment;
import top.ttxxly.com.pictureviewer.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private List<Fragment> data;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView mHome;
    private TextView mCategory;
    private TextView mMyPhoto;
    private TextView mPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();//初始化数据集
        initView();// 初始化控件
        initClickListener();// 事件侦听
        viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager(),data);//初始化适配器类
        mViewPager.setAdapter(viewPagerAdapter);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mHome = (TextView) findViewById(R.id.tv_home);
        mCategory = (TextView) findViewById(R.id.tv_category);
        mMyPhoto = (TextView) findViewById(R.id.tv_my_photo);
        mPeople = (TextView) findViewById(R.id.tv_people);
    }

    /**
     * 设置侦听事件。
     * */
    private void initClickListener() {

        mHome.setOnClickListener(this);
        mCategory.setOnClickListener(this);
        mMyPhoto.setOnClickListener(this);
        mPeople.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {//ViewPager滑动切换监听
            @Override
            public void onPageSelected(int arg0) {
                int currentItem=mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetBottomBar();
                        mHome.setTextColor(Color.parseColor("#227700"));
                        break;
                    case 1:
                        resetBottomBar();
                        mCategory.setTextColor(Color.parseColor("#227700"));
                        break;
                    case 2:
                        resetBottomBar();
                        mMyPhoto.setTextColor(Color.parseColor("#227700"));
                        break;
                    case 3:
                        resetBottomBar();
                        mPeople.setTextColor(Color.parseColor("#227700"));
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initData() {
        data=new ArrayList<Fragment>();
        data.add(new HomeFragment());
        data.add(new ClassificationFragment());
        data.add(new MyPhotoFragment());
        data.add(new PeopleFragment());
    }

    @Override
    public void onClick(View v) {
        resetBottomBar();   //重置颜色
        switch (v.getId()) {
            case R.id.tv_home:
                mViewPager.setCurrentItem(0);
                mHome.setTextColor(Color.parseColor("#227700"));
                break;
            case R.id.tv_category:
                mViewPager.setCurrentItem(1);
                mCategory.setTextColor(Color.parseColor("#227700"));
                break;
            case R.id.tv_my_photo:
                mViewPager.setCurrentItem(2);
                mMyPhoto.setTextColor(Color.parseColor("#227700"));
                break;
            case R.id.tv_people:
                mViewPager.setCurrentItem(3);
                mPeople.setTextColor(Color.parseColor("#227700"));
                break;
        }
    }

    /**
     * 重置字体颜色
     * */
    private void resetBottomBar(){
        mHome.setTextColor(Color.parseColor("#DDDDDD"));
        mCategory.setTextColor(Color.parseColor("#DDDDDD"));
        mMyPhoto.setTextColor(Color.parseColor("#DDDDDD"));
        mPeople.setTextColor(Color.parseColor("#DDDDDD"));
    }
}
