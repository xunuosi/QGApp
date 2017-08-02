package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/31.
 * 资源库视频Entity
 */

public class DBResVideoEntity {
    private String id;
    private String name;
    private String source;
    private String cover;
    private String abs;
    // Video Url
    private String video;

    public DBResVideoEntity() {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
