package sinolight.cn.qgapp.data.bean;

/**
 * Created by xns on 2017/7/10.
 * 行业知识库封面的实体类
 */

public class DataBaseBean {
    private String id;
    private String name;
    private String cover;
    private String abs;

    public DataBaseBean() {
    }

    public DataBaseBean(String id, String name, String cover, String abs) {
        this.id = id;
        this.name = name;
        this.cover = cover;
        this.abs = abs;
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

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    @Override
    public String toString() {
        return "DataBaseBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", abs='" + abs + '\'' +
                '}';
    }
}
