package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.data.bean.CollectEvent;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResPicEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IPicListActivityView;

/**
 * Created by xns on 2017/6/29.
 * MaterialList Presenter
 */

public class PicListActivityPresenter extends BasePresenter<IPicListActivityView, HttpManager> {
    private static final String TAG = "PicListActivityPresenter";
    private Context mContext;

    // 获取资源列表
    private int page = 1;
    private static final int SIZE = 10;
    // 获取当前资源列表的总数
    private int count = 0;
    // 判断当前操作是否为加载更多数据
    private boolean action_more = false;
    private boolean action_search = false;

    private String picSetID;

    private List<KDBResData> mDatas = new ArrayList<>();
    private List<ResImgEntity> picDatas;
    private KDBResAdapter mAdapter;

    private HttpSubscriber<PageEntity<List<ResImgEntity>>> mPicObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<ResImgEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<ResImgEntity>> pageEntity) {
                    if (pageEntity != null) {
                        count = pageEntity.getCount();
                        picDatas = pageEntity.getData();
                        showSuccess();
                    } else {
                        showError(0, null);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mPicObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError(code, errorMsg);
                }
            });

    private void showSuccess() {
        transformKDBResData();
    }

    /**
     * clear data display empty
     */
    private void clearData() {
        if (mAdapter != null) {
            mAdapter.setData(mDatas);
        }
    }

    private void showError(int code, String msg) {
        view().showRefreshing(false);
        if (action_search) {
            showErrorToast(R.string.attention_data_is_empty);
        } else {
            showErrorToast(R.string.attention_data_refresh_error);
        }
        // if data is not obtained,you need clear data show empty.
        clearData();
    }

    private void transformKDBResData() {
        List<KDBResData> list = new ArrayList<>();

        list = KDBResDataMapper.transformImgDatas(picDatas, KDBResAdapter.TYPE_IMG , false);

        // Load More Action
        if (action_more) {
            mDatas.addAll(list);
        } else {// Refresh Action
            mDatas = list;
        }

        showWithData();
    }

    private void showWithData() {
        if (mAdapter == null) {
            mAdapter = new KDBResAdapter(mContext, mDatas);
        } else {
            mAdapter.setData(mDatas);
        }
        view().showListView(mAdapter);
        if (action_more) {
            view().showLoadMoreing(false);
        } else {
            closeRefreshing();
        }

    }

    public PicListActivityPresenter(Context context, IPicListActivityView view) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
        bindView(view);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        mPicObserver.unSubscribe();

        KDBResDataMapper.reset();
        unbindView();
    }

    /**
     * 带参数的请求数据方法
     *
     * @param key
     */
    private void loadDataWithPara(@Nullable String key, boolean isMore, boolean isSearch) {
        action_more = isMore;
        action_search = isSearch;
        // If not load more data, need clear data.
        if (!isMore) {
            mDatas.clear();
        }

        // 请求资源数据
        model.getResPicListNoCache(
                mPicObserver,
                AppHelper.getInstance().getCurrentToken(),
                picSetID,
                key,
                page,
                SIZE
        );

    }

    private void closeRefreshing() {
        if (checkData()) {
            view().showRefreshing(false);
        }
    }

    private void showErrorToast(int resId) {
        view().showToast(resId);
        view().showRefreshing(false);
    }

    /**
     * 验证是否获取完数据
     *
     * @return
     */
    private boolean checkData() {
        return mDatas != null;
    }

    public void refreshView() {
        init2Show();
    }

    public void loadMore(@Nullable String key) {
        if (mDatas != null && mDatas.size() < count) {
            // 有更多数据可以加载
            page++;
            // Action load more data
            loadDataWithPara(key, true, false);
        } else if (mDatas != null && mDatas.size() >= count) {
            // 无更多数据加载
            view().hasMoreData(false);
        } else {
            view().hasMoreData(false);
        }
    }

    public void init2Show() {
        view().hasMoreData(true);
        resetState();
        // load video set data
        getData();
    }

    private void getData() {
        model.getResPicListNoCache(
                mPicObserver,
                AppHelper.getInstance().getCurrentToken(),
                picSetID,
                null,
                page,
                SIZE
        );

    }

    public void searchData(String key) {
        if (checkData(key)) {
            view().showToast(R.string.text_search_data_empty);
            return;
        }
        resetState();
        this.action_search = true;
        this.loadDataWithPara(key, false, true);
    }

    private boolean checkData(String key) {
        return TextUtils.isEmpty(key);
    }

    /**
     * 重置状态
     */
    @Override
    protected void resetState() {
        super.resetState();
        page = 1;
        action_more = false;
        action_search = false;
        mDatas.clear();
        count = 0;
    }

    public void checkIntent(Intent intent) {
        if (intent != null) {
            picSetID = intent.getStringExtra(AppContants.Resource.RES_ID);
            view().showRefreshing(true);
        }
    }

    private HttpSubscriber<Object> mCollectObserver = new HttpSubscriber<>(
            new OnResultCallBack<Object>() {

                @Override
                public void onSuccess(Object o) {
                    showErrorToast(R.string.text_collect_success);
                    view().showRefreshing(false);
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mCollectObserver code:" + code + ",errorMsg:" + errorMsg);
                    checkoutCollectState(code, errorMsg);
                }
            });

    public void collectRes(CollectEvent event) {
        model.collectResNoCache(
                mCollectObserver,
                AppHelper.getInstance().getCurrentToken(),
                event.getResType().getType(),
                event.getId(),
                event.getAction()
        );
    }

    private void checkoutCollectState(int code, String errorMsg) {
        if (code == AppContants.SUCCESS_CODE) {
            view().showStrToast(errorMsg);
            // refresh
           getData();
        } else {
            showError(code, errorMsg);
        }
    }
}
