package sinolight.cn.qgapp.presenter;

import android.content.Context;

import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.views.view.IDBResActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity Presenter
 */

public class DBResActivityPresenter extends BasePresenter<IDBResActivityView,HttpManager> {
    private static final String TAG = "DBResActivityPresenter";
    private Context mContext;

    public DBResActivityPresenter(IDBResActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        unbindView();
    }
}
