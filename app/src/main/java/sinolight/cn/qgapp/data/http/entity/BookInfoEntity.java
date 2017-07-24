package sinolight.cn.qgapp.data.http.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xns on 2017/7/20.
 * 图书详情Entity
 */

public class BookInfoEntity implements Parcelable {
    private String id;
    private String name;
    private String cover;
    private String versionprint;
    private int page;
    private String printtime;
    // 出版时间
    private String issuedate;
    // 出版社
    private String issuedept;
    private String author;
    // 开本
    private String format;
    // 包装
    private String zzformat;
    private String isbn;
    // 分类
    private String classification;
    private String abs;
    // 目录
    private String catalog;

    public BookInfoEntity() {
    }

    protected BookInfoEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        cover = in.readString();
        versionprint = in.readString();
        page = in.readInt();
        printtime = in.readString();
        issuedate = in.readString();
        issuedept = in.readString();
        author = in.readString();
        format = in.readString();
        zzformat = in.readString();
        isbn = in.readString();
        classification = in.readString();
        abs = in.readString();
        catalog = in.readString();
    }

    public static final Creator<BookInfoEntity> CREATOR = new Creator<BookInfoEntity>() {
        @Override
        public BookInfoEntity createFromParcel(Parcel in) {
            return new BookInfoEntity(in);
        }

        @Override
        public BookInfoEntity[] newArray(int size) {
            return new BookInfoEntity[size];
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

    public String getVersionprint() {
        return versionprint;
    }

    public void setVersionprint(String versionprint) {
        this.versionprint = versionprint;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getPrinttime() {
        return printtime;
    }

    public void setPrinttime(String printtime) {
        this.printtime = printtime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getZzformat() {
        return zzformat;
    }

    public void setZzformat(String zzformat) {
        this.zzformat = zzformat;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getIssuedept() {
        return issuedept;
    }

    public void setIssuedept(String issuedept) {
        this.issuedept = issuedept;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(cover);
        parcel.writeString(versionprint);
        parcel.writeInt(page);
        parcel.writeString(printtime);
        parcel.writeString(issuedate);
        parcel.writeString(issuedept);
        parcel.writeString(author);
        parcel.writeString(format);
        parcel.writeString(zzformat);
        parcel.writeString(isbn);
        parcel.writeString(classification);
        parcel.writeString(abs);
        parcel.writeString(catalog);
    }
}
