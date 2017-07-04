package top.ttxxly.com.pictureviewer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import top.ttxxly.com.pictureviewer.R;

/***
 * 修改 nickname
 * 修改 手机号
 */


public class EditInfo1Activity extends AppCompatActivity {

    final int EDIT_PORTRAIT_CODE = 1;    //修改 portrait
    final int EDIT_NICKNAME_CODE = 2;    //修改nickname
    final int EDIT_MOBILE_CODE = 3;    //修改 mobile
    final int EDIT_PASSWORD_CODE = 4;    //修改 password
    final int NO_RESULT_CODE = 0;       //直接返回不做任何的修改


    private EditText mContent;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info1);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        code = extras.getInt("code");
        Log.i("name", name);
        TextView mName = (TextView) findViewById(R.id.tv_edit_info1_name);
        mContent = (EditText) findViewById(R.id.et_edit_info1_content);
        ImageView mReturn = (ImageView) findViewById(R.id.img_edit_info1_return_top);
        Button mConfirm = (Button) findViewById(R.id.btn_edit_info1_confirm);

        //设置返回，当数据为空时
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(NO_RESULT_CODE);
                finish();
            }
        });

        //设置页面描述
        mName.setText(name);

        //确定返回数据的点击事件
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("content", mContent.getText().toString());
                intent.putExtras(bundle);
                setResult(code, intent);
                finish();
            }
        });


    }
}
