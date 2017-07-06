package top.ttxxly.com.pictureviewer.models;

/**
 * Created by ttxxly on 2017/6/28.
 */

public class Register {
    public String flat;         //返回的注册状态，是否注册成功
    public String message;      //返回的注册信息

    public String getFlat() {
        return flat;
    }

    public String getMessage() {
        return message;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
