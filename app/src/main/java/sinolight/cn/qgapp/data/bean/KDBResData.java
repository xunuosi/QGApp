package sinolight.cn.qgapp.data.bean;


/**
 * Created by admin on 2017/7/4.
 * 知识库资源数据的包装类
 */

public class KDBResData<T> {
    private T data;
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

    public KDBResData() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    @Override
    public String toString() {
        return "KDBResData{" +
                "data=" + data +
                ", isLocal=" + isLocal +
                ", itemType=" + itemType +
                ", isSpan=" + isSpan +
                '}';
    }
}
