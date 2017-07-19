package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/19.
 * 资源词条Entity
 */

public class ResWordEntity {
    private String id;
    private String name;
    private String source;

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
}
