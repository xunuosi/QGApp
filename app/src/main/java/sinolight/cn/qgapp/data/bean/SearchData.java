package sinolight.cn.qgapp.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xns on 2017/8/30.
 * history of search data
 */
@Entity(nameInDb = "tb_search")
public class SearchData {
    @Id(autoincrement = true)
    private Long id;
    private String key;

    public SearchData() {
    }

    @Generated(hash = 1849712303)
    public SearchData(Long id, String key) {
        this.id = id;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
