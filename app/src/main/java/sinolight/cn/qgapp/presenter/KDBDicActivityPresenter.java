package sinolight.cn.qgapp.presenter;

import android.content.Context;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.views.view.IKDBDicDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity Presenter
 */

public class KDBDicActivityPresenter extends BasePresenter<IKDBDicDetailActivityView, HttpManager> {
    private static final String TAG = "KDBDicActivityPresenter";

    private Context mContext;

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
}
