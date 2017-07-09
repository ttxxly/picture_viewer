package top.ttxxly.com.pictureviewer.models;

import java.util.Map;
import java.util.Objects;

/**
 * Created by ttxxly on 2017/7/9.
 */

public class Photos {


    private String count;
    private String flat;
    private String message;
    private Map<String,Objects> photo;

    public Map<String, Objects> getPhoto() {
        return photo;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
