package sinolight.cn.qgapp.data.http.entity;

import java.util.List;

/**
 * Created by xns on 2017/8/4.
 * Cook Entity
 */

public class CookEntity<T> {
    private String id;
    private String name;
    private String cover;
    private String source;
    private boolean hascover;
    private List<T> data;

    public CookEntity() {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isHascover() {
        return hascover;
    }

    public void setHascover(boolean hascover) {
        this.hascover = hascover;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
