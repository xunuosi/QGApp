package sinolight.cn.qgapp.exception;

/**
 * Created by xns on 2017/6/29.
 * 网络连接错误的异常类
 */

public class NetworkConnectionException extends Exception {

    public NetworkConnectionException() {
        super();
    }

    public NetworkConnectionException(final Throwable cause) {
        super(cause);
    }
}
