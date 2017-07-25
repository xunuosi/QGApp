package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/7/25.
 * User Entity
 */

public class UserEntity {
    private String username;
    private String phone;
    private String email;

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
