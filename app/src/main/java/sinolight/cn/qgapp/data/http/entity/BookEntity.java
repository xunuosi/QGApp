package sinolight.cn.qgapp.data.http.entity;


/**
 * Created by xns on 2017/7/17.3
 * 知识库图书Entity
 */

public class BookEntity {
    private String id;
    private String name;
    private String cover;
    private String abs;
    private String author;
    private String date;

    public BookEntity() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", abs='" + abs + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
