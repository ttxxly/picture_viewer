package top.ttxxly.com.pictureviewer.models;

import java.util.List;

/**
 * Created by ttxxly on 2017/7/6.
 *
 * 涉及的接口：
 *     显示所有的分类接口
 *     显示当前用户的分类接口
 *     删除分类接口
 *     修改分类接口
 *     添加分类接口
 */

public class Category {

    /**
     * flat : success
     * message : 搜索分类成功
     * arra : ["这是标题","这是标题"]
     */

    private String flat;
    private String message;
    private List<String> arra;

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

    public List<String> getArra() {
        return arra;
    }

    public void setArra(List<String> arra) {
        this.arra = arra;
    }
}
