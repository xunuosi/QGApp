package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;


import java.io.File;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.utils.DataCleanManager;
import sinolight.cn.qgapp.views.activity.AboutActivity;
import sinolight.cn.qgapp.views.view.ISystemActivityView;

/**
 * Created by xns on 2017/7/8.
 * 知识库界面的Presenter
 */

public class SystemActivityPresenter extends BasePresenter<ISystemActivityView, HttpManager> {
    private static final String TAG = "SystemActivityPresenter";
    private Context mContext;

    public SystemActivityPresenter(Context context, ISystemActivityView view) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    private void showSuccess() {

    }

    private void showError(int code, String msg) {
        if (code != AppContants.SUCCESS_CODE) {
            view().showToast(R.string.attention_data_refresh_error);
        } else {
            view().showToast(R.string.text_my_database_is_empty);
        }

    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        unbindView();
    }

    public void init2Show() {
        resetState();
        getData();
    }

    private void getData() {

    }

    @Override
    protected void resetState() {
        super.resetState();
    }

    public void gotoAboutActivity() {
        Intent callIntent = AboutActivity.getCallIntent(mContext);
        view().gotoActivity(callIntent);
    }

    public void clearCache() {
        String cache = null;
        try {
            cache = DataCleanManager.getTotalCacheSize(mContext);
            DataCleanManager.clearAllCache(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        view().showToast(String.format(mContext.getString(R.string.text_clear_success_format), cache));
    }
}
