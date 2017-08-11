package sinolight.cn.qgapp.data.bean;

/**
 * Created by xns on 2017/8/11.
 * Event action
 */

public enum  EventAction {
    ACTION_RESET("action_reset");

    private final String action;

    EventAction(String value) {
        action = value;
    }

    public String getAction() {
        return action;
    }
}
