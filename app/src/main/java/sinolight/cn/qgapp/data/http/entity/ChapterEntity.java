package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/8/3.
 * Chapter Entity
 */

public class ChapterEntity {
    private String id;
    // 所属图书或标准的ID
    private String pid;
    private String name;
    // 序号
    private String order;

    public ChapterEntity() {
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
