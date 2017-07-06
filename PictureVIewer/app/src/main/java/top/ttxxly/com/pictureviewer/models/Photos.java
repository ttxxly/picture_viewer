package top.ttxxly.com.pictureviewer.models;

/**
 * Created by ttxxly on 2017/7/1.
 * 涉及的接口：
 *      图片详细信息修改接口
 *      图片详细信息显示接口
 *      图片删除接口
 *      图片添加接口
 */

public class Photos {
    private String flat;            //状态
    private String message;         //信息
    private long photoid;           //该图片的唯一标识，用于图片修改，删除
    private String datetime;        //该图片的创建时间
    private long categoryid;        //该图片所属的分类的id
    private long userid;            //该图片所属的用户ID
    private String url;             //图片地址

    private String title;           //图片标题
    private String keyword;         //关键词
    private String description;     //图片描述

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlat() {
        return flat;
    }

    public String getMessage() {
        return message;
    }

    public long getPhotoid() {
        return photoid;
    }

    public String getDatetime() {
        return datetime;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public long getUserid() {
        return userid;
    }

    public String getUrl() {
        return url;
    }
}
