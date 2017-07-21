package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IKDBDicDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity Presenter
 */

public class KDBDicActivityPresenter extends BasePresenter<IKDBDicDetailActivityView, HttpManager> {
    private static final String TAG = "KDBDicActivityPresenter";

    private Context mContext;
    private String resId;

    public KDBDicActivityPresenter(Context context, IKDBDicDetailActivityView view) {
        mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {

    }

    public void init2show(Intent intent) {
        if (intent != null) {
            resId = intent.getStringExtra(AppContants.Resource.RES_ID);
            L.d(TAG, "resId:" + resId);
        }
    }
}
