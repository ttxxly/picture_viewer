package top.ttxxly.com.pictureviewer.models;

/**
 * Created by ttxxly on 2017/6/27.
 *
 * 涉及的接口：
 *      登录接口：
 *      注册接口：
 *      修改用户信息接口：
 */

public class User {
    private String flat;             //状态
    private String message;          //信息
    private long userid;             //用户ID， 唯一不变量

    private String nickname;         //用户昵称
    private String password;         //用户密码
    private String mobile;           //用户手机号
    private String portrait;         //头像

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPortrait() {
        return portrait;
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

    public long getUserid() {
        return userid;
    }
}
