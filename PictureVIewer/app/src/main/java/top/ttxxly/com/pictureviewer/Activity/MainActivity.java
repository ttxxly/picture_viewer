package top.ttxxly.com.pictureviewer.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import top.ttxxly.com.pictureviewer.Adapter.ViewPagerAdapter;
import top.ttxxly.com.pictureviewer.Fragment.CategoryFragment;
import top.ttxxly.com.pictureviewer.Fragment.HomeFragment;
import top.ttxxly.com.pictureviewer.Fragment.MyPhotoFragment;
import top.ttxxly.com.pictureviewer.Fragment.PeopleFragment;
import top.ttxxly.com.pictureviewer.PopupWindow.AddPhotosPopupWindow;
import top.ttxxly.com.pictureviewer.PopupWindow.MenuPopupWindow;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.CircleImg;
import top.ttxxly.com.pictureviewer.Utils.FileUtil;
import top.ttxxly.com.pictureviewer.Utils.NetUtil;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private List<Fragment> data;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView mHome;
    private TextView mCategory;
    private TextView mMyPhoto;
    private TextView mPeople;
    private ImageView mAdd_pictures;
    private AlertDialog.Builder builder;
    private AddPhotosPopupWindow menuWindow;

    // 上传服务器的路径【一般不硬编码到程序中】
    private String imgUrl = "http://10.0.2.2/picture_viewer/interface/upload_pictures.php";

    private static final int REQUESTCODE_PICK = 4;		// 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;		// 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;	// 图片裁切标记
    private static final int REQUESTCODE_UPLOAD = 3;    //上传图片标记
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 头像文件名称
    private String urlpath;			// 图片本地路径
    private String resultStr = "";	// 服务端返回结果集
    private static ProgressDialog pd;// 等待进度圈
    private CircleImg avatarImg;// 头像图片
    public static MainActivity mContext = null;
    private ImageView mSearch;
    private TextView mDescription;

    private String title = "";
    private String keywords = "";
    private String description = "";
    private ImageView mManager_category;
    private MenuPopupWindow managerCategory;
    private Bitmap photo;
    private Uri address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;


        initData();//初始化数据集
        initView();// 初始化控件
        initClickListener();// 事件侦听
        resetBottomBar();
        mHome.setTextColor(Color.parseColor("#227700"));
        viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager(),data);//初始化适配器类
        mViewPager.setAdapter(viewPagerAdapter);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mHome = (TextView) findViewById(R.id.tv_home);
        mCategory = (TextView) findViewById(R.id.tv_category);
        mMyPhoto = (TextView) findViewById(R.id.tv_my_photo);
        mPeople = (TextView) findViewById(R.id.tv_people);
        mAdd_pictures = (ImageView) findViewById(R.id.img_add_pictures);
        mSearch = (ImageView) findViewById(R.id.img_search);
        mDescription = (TextView) findViewById(R.id.tv_main_activity_description);
        mManager_category = (ImageView) findViewById(R.id.img_manager_category);
    }

    /**
     * 设置侦听事件。
     * */
    private void initClickListener() {

        mHome.setOnClickListener(this);
        mCategory.setOnClickListener(this);
        mMyPhoto.setOnClickListener(this);
        mPeople.setOnClickListener(this);
        mAdd_pictures.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mManager_category.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {//ViewPager滑动切换监听
            @Override
            public void onPageSelected(int arg0) {
                int currentItem=mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetBottomBar();
                        mHome.setTextColor(Color.parseColor("#227700"));
                        mDescription.setText("主页");
                        break;
                    case 1:
                        resetBottomBar();
                        mCategory.setTextColor(Color.parseColor("#227700"));
                        mDescription.setText("我的分类");
                        break;
                    case 2:
                        resetBottomBar();
                        mMyPhoto.setTextColor(Color.parseColor("#227700"));
                        mDescription.setText("我的图片");
                        break;
                    case 3:
                        resetBottomBar();
                        mPeople.setTextColor(Color.parseColor("#227700"));
                        mDescription.setText("个人中心");
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
        data.add(new CategoryFragment());
        data.add(new MyPhotoFragment());
        data.add(new PeopleFragment());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_home:
                resetBottomBar();   //重置颜色
                mViewPager.setCurrentItem(0, false);
                mHome.setTextColor(Color.parseColor("#227700"));
                break;
            case R.id.tv_category:
                resetBottomBar();   //重置颜色
                mViewPager.setCurrentItem(1, false);
                mCategory.setTextColor(Color.parseColor("#227700"));
                break;
            case R.id.tv_my_photo:
                resetBottomBar();   //重置颜色
                mViewPager.setCurrentItem(2, false);
                mMyPhoto.setTextColor(Color.parseColor("#227700"));
                break;
            case R.id.tv_people:
                resetBottomBar();   //重置颜色
                mViewPager.setCurrentItem(3, false);
                mPeople.setTextColor(Color.parseColor("#227700"));
                break;
            case R.id.img_add_pictures:
                menuWindow = new AddPhotosPopupWindow(this, itemsOnClick);
                menuWindow.showAtLocation(findViewById(R.id.LL_MainLayout),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                //startActivity(new Intent(getApplicationContext(), AddPhotosActivity.class));
                break;
            case R.id.img_search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case R.id.img_manager_category:
                //管理分类页面
                managerCategory = new MenuPopupWindow(this, itemsOnClick);
                managerCategory.showAtLocation(findViewById(R.id.LL_MainLayout),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                Log.i("弹窗", "下拉菜单");
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 拍照
                case R.id.takePhotoBtn:
                    menuWindow.dismiss();
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                   // ClipData.Item.getUri()
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                // 相册选择图片
                case R.id.pickPhotoBtn:
                    menuWindow.dismiss();
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                case R.id.cancelBtn:
                    menuWindow.dismiss();
                    break;
                case R.id.btn_menu_add_category:
                    managerCategory.dismiss();
                    startActivity(new Intent(getApplicationContext(), AddCategoryActivity.class));
                    break;
                case R.id.btn_menu_delete_category:
                    managerCategory.dismiss();
                    startActivity(new Intent(getApplicationContext(), DeleteCategoryActivity.class));
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return ;
        }
        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    urlpath  = data.getData().toString();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(urlpath)) {
                    Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                    intent.putExtra("url", urlpath);
                    Log.i("2222222221urlPath", urlpath);
                    try {
                        address = data.getData();
                        photo = MediaStore.Images.Media.getBitmap(getContentResolver(), address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivityForResult(intent, REQUESTCODE_UPLOAD);
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                urlpath = Uri.fromFile(temp).toString();
                if (!TextUtils.isEmpty(urlpath)) {
                    Intent intent1 = new Intent(getApplicationContext(), UploadActivity.class);
                    intent1.putExtra("url", urlpath);
                    Log.i("88888888888888", urlpath);
                    try {

                        address = Uri.fromFile(temp);
                        photo = MediaStore.Images.Media.getBitmap(getContentResolver(), address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivityForResult(intent1, REQUESTCODE_UPLOAD);
                }
                break;
            case REQUESTCODE_UPLOAD:    //上传图片
                Bundle bundle = data.getExtras();
                title = bundle.getString("title");
                keywords = bundle.getString("keywords");
                description = bundle.getString("description");
                if(photo != null) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                    urlpath = FileUtil.saveFile(MainActivity.this, df.format(new Date())+".jpg", photo);
                    Log.i("图片名称123123", df.format(new Date())+".jpg"+"1234564321234");
                    Log.i("图片路径123123", urlpath);
                    // 新线程后台上传服务端
                    pd = ProgressDialog.show(mContext, null, "正在上传图片，请稍候...");
                    Log.i("11111111111111urlPath", urlpath);
                    new Thread(uploadImageRunnable).start();
                }else
                    Toast.makeText(getApplicationContext(), "位图为空", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "false");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Log.i("裁剪图片", "成功");
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            urlpath = FileUtil.saveFile(MainActivity.this, df.format(new Date())+".jpg", photo);
            Log.i("图片名称", df.format(new Date())+".jpg");
            //avatarImg.setImageDrawable(drawable);


        }else {
            Log.i("裁剪图片", "失败");
        }
    }

    /**
     * 使用HttpUrlConnection模拟post表单进行文件
     * 原理是： 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
     */
    Runnable uploadImageRunnable = new Runnable() {

        private String userid;
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
                textParams.put("title", title);
                textParams.put("keywords", keywords);
                textParams.put("description", description);
                textParams.put("userid", userid);

                // 利用HttpURLConnection对象从网络中获取网页数据
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
                // 对响应码进行判断
                if (code == 200) {// 返回的响应码200,是成功
                    // 得到网络返回的输入流
                    InputStream is = conn.getInputStream();
                    resultStr = NetUtil.readString(is);
                    file.delete();  //删除暂存的图片文件
                } else {
                    Log.i("上传错误", "请求URL失败！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
        }
    };

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pd.dismiss();
                    Log.i("上传返回信息", resultStr);
                    Toast.makeText(MainActivity.this, "我一个马步上传，当当当！！！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 重置字体颜色
     * */
    private void resetBottomBar(){
        mHome.setTextColor(Color.parseColor("#CACDD0"));
        mCategory.setTextColor(Color.parseColor("#CACDD0"));
        mMyPhoto.setTextColor(Color.parseColor("#CACDD0"));
        mPeople.setTextColor(Color.parseColor("#CACDD0"));
    }

}
