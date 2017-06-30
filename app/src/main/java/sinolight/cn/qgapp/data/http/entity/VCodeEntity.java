package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/6/30.
 * 验证码Entity
 */

public class VCodeEntity {
    private String vcode;

    public VCodeEntity() {
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    @Override
    public String toString() {
        return "VCodeEntity{" +
                "vcode='" + vcode + '\'' +
                '}';
    }
}
