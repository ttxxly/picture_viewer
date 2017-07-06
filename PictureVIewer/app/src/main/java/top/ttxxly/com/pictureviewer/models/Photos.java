package top.ttxxly.com.pictureviewer.models;

/**
 * Created by ttxxly on 2017/7/1.
 */

public class Photos {
    private String title;           //图片标题
    private String category;        //分类
    private String keyword;         //关键词
    private String date;            //创建时期
    private String description;     //图片描述

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
