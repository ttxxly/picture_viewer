package top.ttxxly.com.pictureviewer.models;

/**
 * Created by ttxxly on 2017/6/27.
 * 'flat'=>'success',
 * 'message'=>'登陆成功',
 * 'nickname'=>$row->nickname,
 * 'password'=>$row->password,
 * 'mobile'=>$row->mobile,
 * 'portrait'=>$row->portrait
 */

public class Login {
    public String flat;             //登录状态
    public String message;          //登录信息
    public String nickname;         //用户昵称
    public String password;         //用户密码
    public String mobile;           //用户手机
    public String portrait;         //用户头像
    public String nickname1;        //客户端上传的昵称，用于测试
    public String password1;        //客户端上传的密码，用于测试
    public String mobile1;          //客户端上传的手机号，用于测试

    public String getNickname1() {
        return nickname1;
    }

    public String getPassword1() {
        return password1;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setNickname1(String nickname1) {
        this.nickname1 = nickname1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }



    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getFlat() {
        return flat;
    }

    public String getMessage() {
        return message;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPortrait() {
        return portrait;
    }
}
