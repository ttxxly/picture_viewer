package top.ttxxly.com.pictureviewer.models;

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
    private String flat;        //状态
    private String message;     //信息
    private long categoryid;    //该分类的id
    private long userid;        //用户ID
    private String datetime;    //创建日期

    private String title;       //分类名
    private String description; //分类描述
    private String keywords;    //分类关键词
    private String[] photos;    //该分类下的所有图片 URL

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String getFlat() {
        return flat;
    }

    public String getMessage() {
        return message;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public long getUserid() {
        return userid;
    }

    public String getDatetime() {
        return datetime;
    }
}
