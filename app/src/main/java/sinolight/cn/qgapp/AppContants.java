package sinolight.cn.qgapp;

/**
 * Created by xns on 2017/6/29.
 * 常量值
 */

public interface AppContants {
    String BASE_URL = "http://192.168.101.215:9011/api/";
//    String BASE_URL = "http:/192.168.25.183:8018/api/";
    int SUCCESS_CODE = -1;
    int FAILED_CODE = 0;

    interface Account {
        String USER_NAME = "account_name";
        String PASS_WORD = "account_pwd";
        String TOKEN = "account_token";
        String IS_LOGINED = "account_isLogined";
        String IS_SPLASHACTIVITY = "account_isSplashActivity";
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
            DB_CLOTHING("1"), DB_ELECTROMECHANICAL("13"), DB_WEIGHING("12"),
            DB_COOK("18"), DB_STANDARD("19");
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
            RES_IMG("12"), RES_DIC("22"), RES_INDUSTRY("32"),
            RES_COOK("35"), RES_VIDEO("100");

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
        String RES_NAME = "res_name";
    }

    interface User {
        String USER_BEAN = "user_bean";
    }

    interface Video {
        String SET_ID = "video_set_id";
        String VIDEO_ID = "video_id";
        int TYPE_HOT_VIDEO = 0;
        int TYPE_SET_VIDEO = 1;
        int TYPE_LIST_VIDEO = 2;
    }

    interface Read {
        String READ_ID = "read_id";
        String READ_NAME = "read_name";
        String CHAPTERED_ID = "chaptered_id";
        String READ_RES_TYPE = "read_res_type";

        enum Type implements Read {
            TYPE_BOOK("1"), TYPE_STAND("2"), TYPE_ARTICLE("19"),
            TYPE_INDUSTRY("32");

            private final String type;

            Type(String value) {
                type = value;
            }

            public String getType() {
                return type;
            }
        }
    }

    /**
     * 首页库的enum
     */
    interface HomeStore {
        enum Type implements HomeStore {
            TYPE_DB_KNOWLEDGE("行业知识库"), TYPE_DB_RES("资源库"), TYPE_DB_BAIKE("百科库"),
            TYPE_DB_STANDARD("标准库"), TYPE_EBOOK("电子书城"), TYPE_MASTER("行业专家");

            private final String type;

            Type(String value) {
                type = value;
            }

            public String getType() {
                return type;
            }
        }
    }

    interface EBook {
        enum SortType implements EBook {
            SORT_COMPREHENSIVE("0"), SORT_NEWGOODS("1"), SORT_PRICE("2");

            private final String type;

            SortType(String value) {
                type = value;
            }

            public String getType() {
                return type;
            }
        }

        enum SortOrder implements EBook {
            SORT_POSITIVE("0"), SORT_REVERSE("1");

            private final String type;

            SortOrder(String value) {
                type = value;
            }

            public String getType() {
                return type;
            }
        }
    }

    interface Cook {
        String COOK_ID = "cook_id";
    }
}
