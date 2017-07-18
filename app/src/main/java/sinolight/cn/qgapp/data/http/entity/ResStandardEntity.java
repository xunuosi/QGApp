package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/6.
 * 资源列表最新标准的Entity
 */

public class ResStandardEntity {
    private String id;
    private String name;
    private String cover;
    // 标准号
    private String stdno;
    // 实施时间
    private String imdate;

    public ResStandardEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStdno() {
        return stdno;
    }

    public void setStdno(String stdno) {
        this.stdno = stdno;
    }

    public String getImdate() {
        return imdate;
    }

    public void setImdate(String imdate) {
        this.imdate = imdate;
    }
}
