package top.ttxxly.com.pictureviewer.models;

/**
 * Created by ttxxly on 2017/7/6.
 */

public class Category {
    private String title;       //分类名
    private long id;            //用户ID
    private String description; //分类描述
    private String keywords;    //分类关键词
    private String datetime;    //创建日期
    private String[] photos;    //该分类下的所有图片 URL

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
