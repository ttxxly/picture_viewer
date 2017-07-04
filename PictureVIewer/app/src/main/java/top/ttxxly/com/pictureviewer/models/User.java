package top.ttxxly.com.pictureviewer.models;

/**
 * Created by ttxxly on 2017/6/27.
 */

public class User {
    private String nickname;
    private String password;
    private String mobile;
    private String portrait;    //头像

    public void init() {
        this.nickname = null;
        this.password = null;
        this.mobile = null;
        this.portrait = null;
    }
    public void setNickname(String user) {
        this.nickname = user;
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
