package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import top.ttxxly.com.pictureviewer.R;

public class UploadActivity extends AppCompatActivity {

    private String url;
    private static final int REQUESTCODE_UPLOAD = 3;    //上传图片标记
    private EditText et_upload_title;
    private EditText et_upload_keywords;
    private EditText et_upload_description;
    private ImageView pic;
    private ImageView mReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");

        et_upload_title = (EditText) findViewById(R.id.et_upload_title);
        et_upload_keywords = (EditText) findViewById(R.id.et_upload_keywords);
        et_upload_description = (EditText) findViewById(R.id.et_upload_description);
        pic = (ImageView) findViewById(R.id.img_upload_pic);
        mReturn = (ImageView) findViewById(R.id.img_upload_return_top);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btn_upload_confirm = (Button) findViewById(R.id.btn_upload_confirm);
        Log.i("333333333url", url);
        Glide.with(getApplicationContext()).load(url).placeholder(R.mipmap.login_pic).centerCrop().into(pic);

        btn_upload_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_upload_title.getText().toString();
                String keywords = et_upload_keywords.getText().toString();
                String description = et_upload_description.getText().toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(keywords) && !TextUtils.isEmpty(description)) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("keywords", keywords);
                    bundle.putString("description", description);
                    intent.putExtras(bundle);
                    Log.i("摸摸摸摸", "caocaooocaocoocoa");
                    setResult(REQUESTCODE_UPLOAD, intent);
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
