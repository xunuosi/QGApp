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
}
