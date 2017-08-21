package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/19.
 * 资源图片Entity
 */

public class ResImgEntity {
    private String id;
    private String name;
    private String cover;
    private String abs;
    private String author;
    private boolean isfavor;
    private String title;

    public ResImgEntity() {
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

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public boolean isfavor() {
        return isfavor;
    }

    public void setIsfavor(boolean isfavor) {
        this.isfavor = isfavor;
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
}
