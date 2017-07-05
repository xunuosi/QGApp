package sinolight.cn.qgapp.data.bean;

import java.util.List;

/**
 * Created by admin on 2017/7/4.
 * 首页数据的包装类
 */

public class HomeData {
    private int id;
    private String title;
    private String url;
    private int resId;
    /**
     * 是否为本地数据
     */
    private boolean isLocal;
    /**
     * Adapter Item类型
     */
    private int itemType;
    /**
     * Span Line
     */
    private boolean isSpan;
    // 封装的具体数据
    private List<Object> datas;

    public HomeData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean isSpan() {
        return isSpan;
    }

    public void setSpan(boolean span) {
        isSpan = span;
    }
}
