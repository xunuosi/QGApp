package sinolight.cn.qgapp.data.http.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xns on 2017/7/25.
 * Standard data info entity
 */

public class StdInfoEntity implements Parcelable {
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

    protected StdInfoEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        cover = in.readString();
        stdno = in.readString();
        restdno = in.readString();
        issuedate = in.readString();
        imdate = in.readString();
        dept = in.readString();
        classification = in.readString();
        scope = in.readString();
        catalog = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(cover);
        dest.writeString(stdno);
        dest.writeString(restdno);
        dest.writeString(issuedate);
        dest.writeString(imdate);
        dest.writeString(dept);
        dest.writeString(classification);
        dest.writeString(scope);
        dest.writeString(catalog);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StdInfoEntity> CREATOR = new Creator<StdInfoEntity>() {
        @Override
        public StdInfoEntity createFromParcel(Parcel in) {
            return new StdInfoEntity(in);
        }

        @Override
        public StdInfoEntity[] newArray(int size) {
            return new StdInfoEntity[size];
        }
    };

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
