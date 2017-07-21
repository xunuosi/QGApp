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

    interface Account {
        String USER_NAME = "account_name";
        String PASS_WORD = "account_pwd";
        String TOKEN = "account_token";
        String IS_LOGINED = "account_isLogined";
    }

    interface DataBase {
        String KEY_ID = "key_database_id";
        String KEY_NAME = "key_database_name";
        String KEY_TYPE = "key_database_type";
        String KEY_RES_TYPE = "key_res_type";
        String TREE_PID = "0";
        /**
         * 九大行业库的Enum
         */
        enum Type implements DataBase {
            DB_FOOD("16"), DB_ART("11"), DB_PAPER("17"),
            DB_LEATHER("15"), DB_FURNITURE("14"), DB_PACK("10"),
            DB_CLOTHING("1"), DB_ELECTROMECHANICAL("13"), DB_WEIGHING("12");
            private final String type;

            Type(String value) {
                type = value;
            }

            public String getType() {
                return type;
            }
        }

        /**
         * 六种资源类型的Enum
         */
        enum Res implements DataBase {
            RES_BOOK("1"), RES_STANDARD("2"), RES_ARTICLE("19"),
            RES_IMG("12"), RES_DIC("22"), RES_INDUSTRY("32");

            private final String type;

            Res(String value) {
                type = value;
            }

            public String getType() {
                return type;
            }
        }
    }

    interface Resource {
        String RES_ID = "res_id";
    }
}
