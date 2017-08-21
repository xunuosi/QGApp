package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/8/2.
 * 阅读Entity
 */

public class ReaderEntity {
    private String id;
    private String title;
    private String html;
    private boolean isfavor;

    public ReaderEntity() {
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

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public boolean isfavor() {
        return isfavor;
    }

    public void setIsfavor(boolean isfavor) {
        this.isfavor = isfavor;
    }
}
