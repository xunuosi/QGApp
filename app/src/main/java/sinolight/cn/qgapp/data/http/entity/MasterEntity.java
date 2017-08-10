package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/8/8.
 * Master Entity
 */

public class MasterEntity {
    private String id;
    private String name;
    private String cover;
    // 研究成果
    private String remark;
    // 研究领域
    private String domain;
    // 代表作
    private String works;
    // 简介
    private String abs;

    public MasterEntity() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }
}
