package sinolight.cn.qgapp.presenter;

import android.content.Context;

import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.HelpAdapter;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.HelpDataEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IHelpActivityView;

/**
 * Created by xns on 2017/7/8.
 * 知识库界面的Presenter
 */

public class HelpActivityPresenter extends BasePresenter<IHelpActivityView, HttpManager>{
    private static final String TAG = "HelpActivityPresenter";
    private Context mContext;
    private List<HelpDataEntity> mData;
    private HelpAdapter mAdapter;
    private boolean isUpdate = false;

    private HttpSubscriber<List<HelpDataEntity>> mHelpObserver = new HttpSubscriber<>(new OnResultCallBack<List<HelpDataEntity>>() {

        @Override
        public void onSuccess(List<HelpDataEntity> list) {
            if (list != null) {
                mData = list;
                showSuccess();
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "mHelpObserver code:" + code + ",errorMsg:" + errorMsg);
            showError(code, errorMsg);
        }
    });

    private void showError(int code, String errorMsg) {
        if (code == AppContants.SUCCESS_CODE) {
            view().showToastByStr(errorMsg);
        } else {
            view().showToastByRes(R.string.error_internet);
        }
    }

    private void showSuccess() {
        if (mAdapter == null) {
            mAdapter = new HelpAdapter(mContext, mData);
        } else {
            mAdapter.setData(mData);
        }
        view().showListView(mAdapter);
        view().showRefreshing(false);
    }

    public HelpActivityPresenter(Context context, IHelpActivityView view) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
        bindView(view);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        mHelpObserver.unSubscribe();
        unbindView();
    }

    public void refresh() {
        getData();
    }

    private void getData() {
        model.getHelpWithCache(
                mHelpObserver,
                AppHelper.getInstance().getCurrentToken(),
                isUpdate
        );
    }
}
