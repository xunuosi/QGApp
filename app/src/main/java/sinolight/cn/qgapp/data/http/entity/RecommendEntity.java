package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/5.
 * 推荐词条的Entity
 */

public class RecommendEntity {
    private String id;
    private String title;

    public RecommendEntity() {
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

    @Override
    public String toString() {
        return "RecommendEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
