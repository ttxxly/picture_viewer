package top.ttxxly.com.pictureviewer.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ttxxly on 2017/7/9.
 */

public class Photos implements Serializable{

    /**
     * count : 2
     * flat : success
     * message : 搜索成功
     * photos : [{"categoryid":"1","datetime":"2017-07-09 18:02:26","description":"这是一张蹄片","keyword":"portrait","photoid":"1","title":"么马达","url":"/photos/portrait/1.jpg","userid":"6"},{"categoryid":"2","datetime":"2017-07-09 18:27:57","description":"第二张图片","keyword":"portrait","photoid":"2","title":"雅士吗","url":"/photos/portrait/2.jpg","userid":"6"}]
     */

    private String count;
    private String flat;
    private String message;
    private List<PhotosBean> photos;

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

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public static class PhotosBean  implements  Serializable{
        /**
         * categoryid : 1
         * datetime : 2017-07-09 18:02:26
         * description : 这是一张蹄片
         * keyword : portrait
         * photoid : 1
         * title : 么马达
         * url : /photos/portrait/1.jpg
         * userid : 6
         */

        private String categoryid;
        private String datetime;
        private String description;
        private String keyword;
        private String photoid;
        private String title;
        private String url;
        private String userid;

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getPhotoid() {
            return photoid;
        }

        public void setPhotoid(String photoid) {
            this.photoid = photoid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
