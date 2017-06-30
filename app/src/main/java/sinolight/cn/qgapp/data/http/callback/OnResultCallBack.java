package sinolight.cn.qgapp.data.http.callback;

public interface OnResultCallBack<T> {

    void onSuccess(T t);

    void onError(int code, String errorMsg);
}
