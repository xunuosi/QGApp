package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/6.
 * 最新标准的Entity
 */

public class StandardEntity {
    private String id;
    private String title;
    private String cover;
    // 标准号
    private String stdno;
    // 实施时间
    private String imdate;

    public StandardEntity() {
    }

    public StandardEntity(String id, String title, String cover, String stdno, String imdate) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.stdno = stdno;
        this.imdate = imdate;
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

    @Override
    public String toString() {
        return "StandardEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", stdno='" + stdno + '\'' +
                ", imdate='" + imdate + '\'' +
                '}';
    }
}
