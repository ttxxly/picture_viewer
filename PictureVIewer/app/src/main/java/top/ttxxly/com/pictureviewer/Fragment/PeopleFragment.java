package top.ttxxly.com.pictureviewer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import top.ttxxly.com.pictureviewer.Activity.EditInfo1Activity;
import top.ttxxly.com.pictureviewer.Activity.EditInfo2Activity;
import top.ttxxly.com.pictureviewer.Activity.LoginActivity;
import top.ttxxly.com.pictureviewer.Activity.MainActivity;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;


public class PeopleFragment extends Fragment implements View.OnClickListener{

    final int EDIT_PORTRAIT_CODE = 1;    //修改 portrait
    final int EDIT_NICKNAME_CODE = 2;    //修改nickname
    final int EDIT_MOBILE_CODE = 3;    //修改 mobile
    final int EDIT_PASSWORD_CODE = 4;    //修改 password
    final int NO_RESULT_CODE = 0;       //直接返回不做任何的修改

    private RelativeLayout rl_portrait;
    private RelativeLayout rl_nickname;
    private RelativeLayout rl_phone;
    private RelativeLayout password;
    private Button logout;
    private TextView nickname;
    private TextView phone;
    private ImageView portrait;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_people, container, false);

        rl_portrait = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_portrait);
        rl_nickname = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_nickname);
        rl_phone = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_phone);
        password = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_editPassword);
        logout = (Button) view.findViewById(R.id.btn_logout);
        nickname = (TextView) view.findViewById(R.id.tv_fragment_people_nickname);
        phone = (TextView) view.findViewById(R.id.tv_fragment_people_phone);
        portrait = (ImageView) view.findViewById(R.id.tv_fragment_people_portrait);
        rl_portrait.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        password.setOnClickListener(this);
        logout.setOnClickListener(this);
        init();
        // Inflate the layout for this fragment
        return view;
    }

    private void init()  {
        nickname.setText(SharedPreferenceUtils.getString("loginNickname", "", getContext()));
        phone.setText(SharedPreferenceUtils.getString("loginMobile", "", getContext()));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RL_fragment_people_portrait:
                Toast.makeText(getContext(), "修改头像", Toast.LENGTH_SHORT).show();
                portrait.setImageResource(R.mipmap.launcher_bg);
                break;
            case R.id.RL_fragment_people_nickname:
                Toast.makeText(getContext(), "修改昵称", Toast.LENGTH_SHORT).show();
                intent = new Intent(getContext(), EditInfo1Activity.class);
                intent.putExtra("name", "修改昵称");
                intent.putExtra("code", EDIT_NICKNAME_CODE);
                startActivityForResult(intent, EDIT_NICKNAME_CODE);
                //
                break;
            case R.id.RL_fragment_people_phone:
                Toast.makeText(getContext(), "修改电话号码", Toast.LENGTH_SHORT).show();
                intent = new Intent(getContext(), EditInfo1Activity.class);
                intent.putExtra("name", "修改电话号码");
                intent.putExtra("code", EDIT_MOBILE_CODE);
                startActivityForResult(intent, EDIT_MOBILE_CODE);
                break;
            case R.id.RL_fragment_people_editPassword:
                Toast.makeText(getContext(), "修改密码", Toast.LENGTH_SHORT).show();
                intent = new Intent(getContext(), EditInfo2Activity.class);
                startActivityForResult(intent, EDIT_PASSWORD_CODE);
                break;
            case R.id.btn_logout:
                SharedPreferenceUtils.putBoolean("loginInfo", false, getContext());
                Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                MainActivity.mContext.finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case NO_RESULT_CODE:
                break;
            case EDIT_NICKNAME_CODE:
                Bundle bundle = data.getExtras();
                String content = bundle.getString("content");
                Log.i("content", content);
                nickname.setText(content);
                break;
        }
    }
}
