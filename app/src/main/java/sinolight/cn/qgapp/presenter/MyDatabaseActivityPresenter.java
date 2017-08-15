package sinolight.cn.qgapp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MyDatabaseAdapter;
import sinolight.cn.qgapp.data.bean.DataBaseBean;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IMyDatabaseActivityView;

/**
 * Created by xns on 2017/7/8.
 * 知识库界面的Presenter
 */

public class MyDatabaseActivityPresenter extends BasePresenter<IMyDatabaseActivityView, HttpManager> {
    private static final String TAG = "MyDatabaseActivityPresenter";
    private Context mContext;
    private List<DataBaseBean> mDataInternet = new ArrayList<>();
    private MyDatabaseAdapter mAdapter;

    private HttpSubscriber databaseObserver = new HttpSubscriber(new OnResultCallBack<List<DataBaseBean>>() {

        @Override
        public void onSuccess(List<DataBaseBean> list) {
            if (list != null && !list.isEmpty()) {
                mDataInternet = list;
                showSuccess();
            } else {
                showError(-1, null);
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "databaseObserver code:" + code + ",errorMsg:" + errorMsg);
            showError(code, errorMsg);
        }
    });

    public MyDatabaseActivityPresenter(Context context, IMyDatabaseActivityView view) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    private void showSuccess() {
        view().setRefreshEnable(false);
        if (mAdapter == null) {
            mAdapter = new MyDatabaseAdapter(mContext, mDataInternet);
        } else {
            mAdapter.setData(mDataInternet);
        }
        view().showListView(mAdapter);
    }

    private void showError(int code, String msg) {
        if (code != AppContants.SUCCESS_CODE) {
            view().showToast(R.string.attention_data_refresh_error);
        } else {
            view().showToast(R.string.text_my_database_is_empty);
        }

        view().setRefreshEnable(false);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        databaseObserver.unSubscribe();
        unbindView();
    }

    public void init2Show() {
        resetState();
        getData();
    }

    private void getData() {
        model.getMyDataBaseWithCache(
                databaseObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

    @Override
    protected void resetState() {
        super.resetState();
        mDataInternet.clear();
    }
}
