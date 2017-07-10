package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/10.
 * 页码信息的json
 */

public class PageEntity<T> {
    private T data;
    private int page;
    private int siez;
    private int count;

    public PageEntity() {
    }

    public PageEntity(T data, int page, int siez, int count) {
        this.data = data;
        this.page = page;
        this.siez = siez;
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSiez() {
        return siez;
    }

    public void setSiez(int siez) {
        this.siez = siez;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PageEntity{" +
                "data=" + data +
                ", page=" + page +
                ", siez=" + siez +
                ", count=" + count +
                '}';
    }
}
