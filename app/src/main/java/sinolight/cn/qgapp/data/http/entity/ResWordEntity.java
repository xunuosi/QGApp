package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/19.
 * 资源词条Entity
 */

public class ResWordEntity {
    private String id;
    private String name;
    private String source;
    private String cover;
    private String remark;

    public ResWordEntity() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
