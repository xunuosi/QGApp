package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/31.
 * 资源库热门图片Entity
 */

public class DBResPicEntity {
    private String id;
    private String name;
    private String abs;
    private String cover;
    private String source;

    public DBResPicEntity() {
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

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
