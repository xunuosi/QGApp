package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/7.
 * 热门文章的Entity
 */

public class ArticleEntity {
    private String id;
    private String title;
    private String author;
    private String source;
    private String remark;

    public ArticleEntity() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", remark='" + remark + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
