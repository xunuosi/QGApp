package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/13.
 * 数据库六大资源分类树
 */

public class DBResTypeEntity {
    private String id;
    private String name;
    private String pid;

    public DBResTypeEntity() {
    }

    public DBResTypeEntity(String id, String name, String pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "DBResTypeEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}
