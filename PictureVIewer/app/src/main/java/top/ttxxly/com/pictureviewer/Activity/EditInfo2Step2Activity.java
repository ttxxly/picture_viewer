package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import top.ttxxly.com.pictureviewer.R;

public class EditInfo2Step2Activity extends AppCompatActivity {

    private EditText mNew_password;
    private EditText mNew_password_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info2_step2);

        mNew_password = (EditText) findViewById(R.id.et_edit_info2_new_password);
        mNew_password_again = (EditText) findViewById(R.id.et_edit_info2_new_password_again);
        ImageView mReturn = (ImageView) findViewById(R.id.img_edit_info2_step2_return_top);
        Button mConfirm = (Button) findViewById(R.id.img_edit_info2_confirm);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditInfo2Activity.class));
                finish();
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mPassword = mNew_password.getText().toString();
                String mPasswordAgain = mNew_password_again.getText().toString();

                if(TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(getApplicationContext(), "输入密码不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(mPasswordAgain)) {
                    Toast.makeText(getApplicationContext(), "再次输入密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    if (mPassword.equals(mPasswordAgain)) {
                        //密码格式正确， 可以确定修改了
                        /**
                         * 1. 远程数据库修改，
                         * 2. 数据库修改后，本地 SharedPreferenceUtils.putString("loginPassword")
                         * 3. finish() ，弹出 “修改成功” 吐司对话框
                         * */
                    }else {
                        Toast.makeText(getApplicationContext(), "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}
