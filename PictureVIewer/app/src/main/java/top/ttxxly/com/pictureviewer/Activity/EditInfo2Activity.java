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

import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;

public class EditInfo2Activity extends AppCompatActivity {

    private String password11;
    private EditText mOriginalPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info2);

        mOriginalPassword = (EditText) findViewById(R.id.et_edit_info2_original_password);
        ImageView mReturn = (ImageView) findViewById(R.id.img_edit_info2_return_top);
        Button next = (Button) findViewById(R.id.img_edit_info2_next);

        //返回上一级的页面
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password11 = mOriginalPassword.getText().toString();

                Log.i("原密码", password11);

                if (!TextUtils.isEmpty(password11)) {
                    String passwd = SharedPreferenceUtils.getString("loginPassword", "", getApplicationContext());
                    System.out.println("密码： " + passwd);
                    if (password11.equals(passwd)) {
                        startActivity(new Intent(getApplicationContext(), EditInfo2Step2Activity.class));
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "密码校验错误", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
