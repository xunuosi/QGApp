package sinolight.cn.qgapp.data.http.entity;

/**
 * Created by xns on 2017/6/30.
 * 通用结果Entity
 * success : true
 * result : {"vcode":"\u201d\u201d"}
 * message : ””
 * code : -1
 * auth : true
 */

public class ResultEntity<T> {
    private boolean success;
    private T result;
    private String message;
    private int code;
    private boolean auth;

    public ResultEntity() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "success=" + success +
                ", result=" + result +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", auth=" + auth +
                '}';
    }
}
