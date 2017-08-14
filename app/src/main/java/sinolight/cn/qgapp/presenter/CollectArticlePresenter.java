package sinolight.cn.qgapp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.CollectEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.ICollectBookFragmentView;

/**
 * Created by xns on 2017/6/29.
 * MaterialList Presenter
 */

public class CollectArticlePresenter extends BasePresenter<ICollectBookFragmentView, HttpManager> {
    private static final String TAG = "CollectArticlePresenter";
    private Context mContext;

    // 获取资源列表
    private int page = 1;
    private static final int SIZE = 10;
    // 获取当前资源列表的总数
    private int count = 0;
    // 判断当前操作是否为加载更多数据
    private boolean action_more = false;

    private String resType;

    private List<KDBResData> mDatas = new ArrayList<>();
    private List<CollectEntity> collectDatas;
    private KDBResAdapter mAdapter;

    private HttpSubscriber<PageEntity<List<CollectEntity>>> mCollectObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<CollectEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<CollectEntity>> pageEntity) {
                    if (pageEntity != null) {
                        count = pageEntity.getCount();
                        collectDatas = pageEntity.getData();
                        transformKDBResData();
                    } else {
                        showError(0, null);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mCollectObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError(code, errorMsg);
                }
            });

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
        view().setHideEmpty(false);
        if (code != AppContants.SUCCESS_CODE) {
            showErrorToast(R.string.attention_data_refresh_error);
        }
        // if data is not obtained,you need clear data show empty.
        clearData();
    }

    private void transformKDBResData() {
        view().setHideEmpty(true);
        List<KDBResData> list = new ArrayList<>();
        list = KDBResDataMapper.transformCollectDatas(collectDatas, KDBResAdapter.TYPE_ARTICLE, false);

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

    public CollectArticlePresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
        resType = AppContants.DataBase.Res.RES_ARTICLE.getType();
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        mCollectObserver.unSubscribe();

        unbindView();
    }

    /**
     * 带参数的请求数据方法
     *
     */
    private void loadDataWithPara(boolean isMore) {
        action_more = isMore;
        // If not load more data, need clear data.
        if (!isMore) {
            mDatas.clear();
        }

        // 请求资源数据
        model.getCollectDataNoCache(
                mCollectObserver,
                AppHelper.getInstance().getCurrentToken(),
                resType,
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

    public void loadMore() {
        if (mDatas != null && mDatas.size() < count) {
            // 有更多数据可以加载
            page++;
            // Action load more data
            loadDataWithPara(true);
        } else if (mDatas != null && mDatas.size() >= count) {
            // 无更多数据加载
            view().hasMoreData(false);
        } else {
            view().hasMoreData(false);
        }
    }

    private void init2Show() {
        view().hasMoreData(true);
        resetState();
        // load video set data
        getData();
    }

    private void getData() {
        model.getCollectDataNoCache(
                mCollectObserver,
                AppHelper.getInstance().getCurrentToken(),
                resType,
                page,
                SIZE
        );

    }

    /**
     * 重置状态
     */
    @Override
    protected void resetState() {
        super.resetState();
        page = 1;
        action_more = false;
        mDatas.clear();
        count = 0;
    }
}
