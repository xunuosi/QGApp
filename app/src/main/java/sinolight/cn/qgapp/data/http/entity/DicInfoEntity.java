package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/20.
 * 图书详情Entity
 */

public class DicInfoEntity {
    private String id;
    private String name;
    private String enname;
    private String source;
    private String content;
    // a flag about have contain of pic
    private boolean picflag;
    private String picpath;
    private String pictitle;
    private boolean isfavor;

    public DicInfoEntity() {
    }

    public boolean isfavor() {
        return isfavor;
    }

    public void setIsfavor(boolean isfavor) {
        this.isfavor = isfavor;
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

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPicflag() {
        return picflag;
    }

    public void setPicflag(boolean picflag) {
        this.picflag = picflag;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public String getPictitle() {
        return pictitle;
    }

    public void setPictitle(String pictitle) {
        this.pictitle = pictitle;
    }
}
