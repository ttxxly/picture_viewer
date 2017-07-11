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

import com.bumptech.glide.Glide;

import top.ttxxly.com.pictureviewer.Activity.EditInfo1Activity;
import top.ttxxly.com.pictureviewer.Activity.EditInfo2Activity;
import top.ttxxly.com.pictureviewer.Activity.EditPortraitActivity;
import top.ttxxly.com.pictureviewer.Activity.LoginActivity;
import top.ttxxly.com.pictureviewer.R;
import top.ttxxly.com.pictureviewer.Utils.SharedPreferenceUtils;

import static top.ttxxly.com.pictureviewer.Activity.MainActivity.mContext;


public class PeopleFragment extends Fragment implements View.OnClickListener{

    final int EDIT_PORTRAIT_CODE = 1;    //修改 portrait
    final int EDIT_NICKNAME_CODE = 2;    //修改nickname
    final int EDIT_MOBILE_CODE = 3;    //修改 mobile
    final int NO_RESULT_CODE = 0;       //直接返回不做任何的修改

    private RelativeLayout rl_portrait;
    private RelativeLayout rl_nickname;
    private RelativeLayout rl_phone;
    private RelativeLayout password;
    private RelativeLayout rl_id;
    private Button logout;
    private TextView nickname;
    private TextView phone;
    private ImageView portrait;
    private TextView id;
    private Intent intent;
    private String URL = "http://10.0.2.2/picture_viewer";
    private Bundle bundle;
    private String content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_people, container, false);

        rl_portrait = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_portrait);
        rl_nickname = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_nickname);
        rl_phone = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_phone);
        rl_id = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_id);
        password = (RelativeLayout) view.findViewById(R.id.RL_fragment_people_editPassword);
        logout = (Button) view.findViewById(R.id.btn_logout);
        nickname = (TextView) view.findViewById(R.id.tv_fragment_people_nickname);
        phone = (TextView) view.findViewById(R.id.tv_fragment_people_phone);
        portrait = (ImageView) view.findViewById(R.id.tv_fragment_people_portrait);
        id = (TextView) view.findViewById(R.id.tv_fragment_people_id);
        rl_portrait.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_id.setOnClickListener(this);
        password.setOnClickListener(this);
        logout.setOnClickListener(this);
        init();
        // Inflate the layout for this fragment
        return view;
    }

    private void init()  {
        String portraitAddress = SharedPreferenceUtils.getString("UserPortrait", "", getContext());
        String userid = SharedPreferenceUtils.getString("UserId", "", getContext());
        Log.i("portraitAddress", portraitAddress);
        Glide.with(getContext()).load(URL + portraitAddress).centerCrop().into(portrait);
        nickname.setText(SharedPreferenceUtils.getString("UserNickname", "", getContext()));
        phone.setText(SharedPreferenceUtils.getString("UserMobile", "", getContext()));
        id.setText(userid);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RL_fragment_people_id:
                Toast.makeText(getContext(), "id不可修改", Toast.LENGTH_SHORT).show();
                Log.i("status", "点击了id");
                break;
            case R.id.RL_fragment_people_portrait:
                Toast.makeText(mContext, "修改头像", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(getContext(), EditPortraitActivity.class), EDIT_PORTRAIT_CODE);
                break;
            case R.id.RL_fragment_people_nickname:
                Toast.makeText(mContext, "修改昵称", Toast.LENGTH_SHORT).show();
                intent = new Intent(getContext(), EditInfo1Activity.class);
                intent.putExtra("name", "修改昵称");
                intent.putExtra("code", EDIT_NICKNAME_CODE);
                startActivityForResult(intent, EDIT_NICKNAME_CODE);
                //
                break;
            case R.id.RL_fragment_people_phone:
                Toast.makeText(mContext, "修改电话号码", Toast.LENGTH_SHORT).show();
                intent = new Intent(getContext(), EditInfo1Activity.class);
                intent.putExtra("name", "修改电话号码");
                intent.putExtra("code", EDIT_MOBILE_CODE);
                startActivityForResult(intent, EDIT_MOBILE_CODE);
                break;
            case R.id.RL_fragment_people_editPassword:
                Toast.makeText(mContext, "修改密码", Toast.LENGTH_SHORT).show();
                intent = new Intent(getContext(), EditInfo2Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                SharedPreferenceUtils.putBoolean("UserStatus", false, getContext());
                Toast.makeText(mContext, "退出成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                mContext.finish();
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
                bundle = data.getExtras();
                content = bundle.getString("content");
                Log.i("nickname_content", content);
                nickname.setText(content);
                break;
            case EDIT_MOBILE_CODE:
                bundle = data.getExtras();
                content = bundle.getString("content");
                Log.i("mobile_content", content);
                phone.setText(content);
                break;
            case EDIT_PORTRAIT_CODE:
                bundle = data.getExtras();
                String url = bundle.getString("url");
                Log.i("url", url);
                Glide.with(getContext()).load(URL + url).centerCrop().into(portrait);
                break;
        }
    }
}
