package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResPicEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IPicSetActivityView;

/**
 * Created by xns on 2017/6/29.
 * MaterialList Presenter
 */

public class PicSetActivityPresenter extends BasePresenter<IPicSetActivityView, HttpManager> {
    private static final String TAG = "PicSetActivityPresenter";
    private Context mContext;

    // 获取资源列表
    private int page = 1;
    private static final int SIZE = 10;
    // 获取当前资源列表的总数
    private int count = 0;
    // 判断当前操作是否为加载更多数据
    private boolean action_more = false;
    private boolean action_search = false;

    private List<KDBResData> mDatas = new ArrayList<>();
    private List<DBResPicEntity> picDatas;
    private CommonTitleAdapter mAdapter;

    private HttpSubscriber<PageEntity<List<DBResPicEntity>>> mPicObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<DBResPicEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<DBResPicEntity>> pageEntity) {
                    if (pageEntity != null) {
                        count = pageEntity.getCount();
                        picDatas = pageEntity.getData();
                        transformKDBResData();
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

        list = KDBResDataMapper.transformPicDatas(picDatas, CommonTitleAdapter.TYPE_PIC_SET , false);

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
            mAdapter = new CommonTitleAdapter(mContext, mDatas);
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

    public PicSetActivityPresenter(Context context, IPicSetActivityView view) {
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
    private void loadDataWithPara(@Nullable String key, @Nullable String themeType, boolean isMore, boolean isSearch) {
        action_more = isMore;
        action_search = isSearch;
        // If not load more data, need clear data.
        if (!isMore) {
            mDatas.clear();
        }

        // 请求资源数据
        model.getResPicSetNoCache(
                mPicObserver,
                AppHelper.getInstance().getCurrentToken(),
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

    public void loadMore(@Nullable String key, @Nullable String themeType) {
        if (mDatas != null && mDatas.size() < count) {
            // 有更多数据可以加载
            page++;
            // Action load more data
            loadDataWithPara(key, themeType, true, false);
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
        model.getResPicSetNoCache(
                mPicObserver,
                AppHelper.getInstance().getCurrentToken(),
                null,
                page,
                SIZE
        );

    }

    public void searchData(String key, String themeType) {
        if (checkData(key)) {
            view().showToast(R.string.text_search_data_empty);
            return;
        }
        resetState();
        this.action_search = true;
        this.loadDataWithPara(key, themeType, false, true);
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
}
