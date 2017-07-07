package sinolight.cn.qgapp;

/**
 * Created by xns on 2017/6/29.
 * 常量值
 */

public interface AppContants {
    String BASE_URL = "http://192.168.101.215:9011/api/";
//    String BASE_URL = "http://192.168.25.139:9005/api/";
    int SUCCESS_CODE = -1;
    int FAILED_CODE = 0;
    //    String baseUrl = "test";

    interface Account {
        String USER_NAME = "account_name";
        String PASS_WORD = "account_pwd";
        String TOKEN = "account_token";
        String IS_LOGINED = "account_isLogined";
    }
}
