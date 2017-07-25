package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/25.
 * Standard data info entity
 */

public class StdInfoEntity {
    private String id;
    private String name;
    private String cover;
    // 标准号
    private String stdno;
    // 替代标准号
    private String restdno;
    // 发布日期
    private String issuedate;
    // 实施日期
    private String imdate;
    // 发布单位
    private String dept;
    private String classification;
    // 适用范围
    private String scope;
    // 目录
    private String catalog;

    public StdInfoEntity() {
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

    public String getStdno() {
        return stdno;
    }

    public void setStdno(String stdno) {
        this.stdno = stdno;
    }

    public String getRestdno() {
        return restdno;
    }

    public void setRestdno(String restdno) {
        this.restdno = restdno;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getImdate() {
        return imdate;
    }

    public void setImdate(String imdate) {
        this.imdate = imdate;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
