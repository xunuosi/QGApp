package sinolight.cn.qgapp.data.http.entity;

import java.util.List;

/**
 * Created by xns on 2017/8/4.
 * 菜谱内容的Entity
 */

public class CookContentEntity {
    private String name;
    private List<String> content;

    public CookContentEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
