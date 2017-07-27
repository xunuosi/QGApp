package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/7.
 * 资源库热门文章的Entity
 */

public class DBResArticleEntity {
    private String id;
    private String name;
    private String source;
    private String author;
    private String remark;

    public DBResArticleEntity() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
