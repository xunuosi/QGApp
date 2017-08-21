package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CookAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.CookContentEntity;
import sinolight.cn.qgapp.data.http.entity.CookEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IMaterialDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * MaterialDetail Presenter
 */

public class MaterialDetailActivityPresenter extends BasePresenter<IMaterialDetailActivityView, HttpManager> {
    private static final String TAG = "MaterialDetailActivityPresenter";
    private Context mContext;

    private String cookID;
    private CookEntity<CookContentEntity> mCookData;
    private List<KDBResData> mDatas = new ArrayList<>();
    private CookAdapter mAdapter;

    private HttpSubscriber<CookEntity<CookContentEntity>> mCookObserver = new HttpSubscriber<>(
            new OnResultCallBack<CookEntity<CookContentEntity>>() {

                @Override
                public void onSuccess(CookEntity<CookContentEntity> cookContentEntityCookEntity) {
                    if (cookContentEntityCookEntity != null) {
                        mCookData = cookContentEntityCookEntity;
                        showSuccess();
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mCookObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

    private void showSuccess() {
        transformKDBResData();
    }

    private void showError() {
        showRefreshView(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void transformKDBResData() {

        Flowable.fromCallable(() -> {
            List<KDBResData> list = new ArrayList<>();
            list = KDBResDataMapper.transformCookInfo(mCookData, CookAdapter.TYPE_HEAD, false);
            return list;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> showWithData(list));

    }

    private void showWithData(List<KDBResData> list) {
        mDatas = list;
        view().showTitle(mCookData.getName());
        view().setCollectState(mCookData.isfavor());

        if (mAdapter == null) {
            mAdapter = new CookAdapter(mContext, mDatas);
        } else {
            mAdapter.setData(mDatas);
        }

        view().showListView(mAdapter);
        showRefreshView(false);
    }

    public MaterialDetailActivityPresenter(IMaterialDetailActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        if (mCookObserver != null) {
            mCookObserver.unSubscribe();
        }
        mCollectObserver.unSubscribe();

        mCookData = null;
        mDatas.clear();
        KDBResDataMapper.reset();
        unbindView();
    }

    private void showErrorToast(int resId) {
        view().showToast(resId);
    }

    private void getData() {
        model.getCookInfoNoCache(
                mCookObserver,
                AppHelper.getInstance().getCurrentToken(),
                cookID
        );
    }

    /**
     * 重置状态
     */
    @Override
    protected void resetState() {
        super.resetState();
    }

    public void checkoutIntent(Intent intent) {
        if (intent == null) {
            return;
        } else {
            cookID = intent.getStringExtra(AppContants.Cook.COOK_ID);
            showRefreshView(true);
            getData();
        }
    }

    private void showRefreshView(boolean enable) {
        view().showRefreshView(enable);
    }

    private HttpSubscriber<Object> mCollectObserver = new HttpSubscriber<>(
            new OnResultCallBack<Object>() {

                @Override
                public void onSuccess(Object o) {
                    showErrorToast(R.string.text_collect_success);
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mCollectObserver code:" + code + ",errorMsg:" + errorMsg);
                    checkoutCollectState(code, errorMsg);
                }
            });

    private void checkoutCollectState(int code, String errorMsg) {
        if (code == AppContants.SUCCESS_CODE) {
//            view().setCollectState(true);
            view().showStrToast(errorMsg);
            // refresh
            getData();
        } else {
            showError();
        }
    }

    public void collectRes(AppContants.DataBase.Res resType) {
        model.collectResNoCache(
                mCollectObserver,
                AppHelper.getInstance().getCurrentToken(),
                resType.getType(),
                cookID,
                getAction()
        );
    }

    private int getAction() {
        if (mCookData.isfavor()) {
            return AppContants.Collect.ACTION_UNCOLLECT;
        } else {
            return AppContants.Collect.ACTION_COLLECT;
        }
    }

}
