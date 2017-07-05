package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/5.
 * 首页轮播图的Entity
 */

public class BannerEntity {
    private String id;
    private String title;
    private String Cover;

    public BannerEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        this.Cover = cover;
    }

    @Override
    public String toString() {
        return "BannerEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + Cover + '\'' +
                '}';
    }
}
