package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/7.
 * 热门文章的Entity
 */

public class ResArticleEntity {
    private String id;
    private String name;
    private String cover;
    private String author;
    private String source;
    private String abs;

    public ResArticleEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }
}
