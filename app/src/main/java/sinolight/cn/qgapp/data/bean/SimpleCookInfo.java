package sinolight.cn.qgapp.data.bean;

/**
 * Created by xns on 2017/8/7.
 * 简化版的CookInfo Bean
 */

public class SimpleCookInfo {
    private String id;
    private String name;
    private String cover;
    private String source;
    private boolean hascover;

    public SimpleCookInfo() {
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
}
