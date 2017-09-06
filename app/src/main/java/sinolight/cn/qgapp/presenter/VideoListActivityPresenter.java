package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.VideoAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IVideoListActivityView;
import sinolight.cn.qgapp.views.view.IVideoListSetActivityView;

/**
 * Created by xns on 2017/6/29.
 * VideoList Presenter
 */

public class VideoListActivityPresenter extends BasePresenter<IVideoListActivityView, HttpManager> {
    private static final String TAG = "VideoListActivityPresenter";
    private Context mContext;

    private AppContants.DataBase.Res resType;
    // 获取资源列表
    private int page = 1;
    private static final int SIZE = 10;
    // 获取当前资源列表的总数
    private int count = 0;
    // 判断当前操作是否为加载更多数据
    private boolean action_more = false;
    private boolean action_search = false;
    // VideoParentID
    private String videoSetID;

    private List<KDBResData> mDatas = new ArrayList<>();
    private List<DBResVideoEntity> videoDatas;
    private VideoAdapter mAdapter;

    private HttpSubscriber<PageEntity<List<DBResVideoEntity>>> mVideoObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<DBResVideoEntity>>>() {

        @Override
        public void onSuccess(PageEntity<List<DBResVideoEntity>> pageEntity) {
            if (pageEntity != null) {
                count = pageEntity.getCount();
                videoDatas = pageEntity.getData();
                showSuccess();
            }

        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "mVideoObserver code:" + code + ",errorMsg:" + errorMsg);
            showError(code, errorMsg);
        }
    });

    private void showSuccess() {
        if (videoDatas != null && videoDatas.size() != 0) {
            transformKDBResData(AppContants.DataBase.Res.RES_VIDEO);
        } else {
            view().showToast(R.string.attention_data_is_empty);
        }
    }

    /**
     * clear data display empty
     */
    private void clearData() {
        if (mAdapter != null) {
            mAdapter.setData(mDatas);
        }
    }

    private void showError(int code, String errorMsg) {
        view().showRefreshing(false);
        if (code==AppContants.SUCCESS_CODE && action_search) {
            showErrorToast(R.string.attention_data_is_empty);
        } else if (code == AppContants.SUCCESS_CODE) {
            view().showToastByStr(errorMsg);
        } else {
            showErrorToast(R.string.attention_data_refresh_error);
        }
        // if data is not obtained,you need clear data show empty.
        clearData();
    }

    private void transformKDBResData(AppContants.DataBase.Res resType) {
        List<KDBResData> list = new ArrayList<>();
        switch (resType) {
            case RES_VIDEO:
                list = KDBResDataMapper.transformVideoDatas(videoDatas, VideoAdapter.TYPE_VIDEO_LIST, false);
                break;
        }
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
            mAdapter = new VideoAdapter(mContext, mDatas);
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

    public VideoListActivityPresenter(IVideoListActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        if (mVideoObserver != null) {
            mVideoObserver.unSubscribe();
        }

        KDBResDataMapper.reset();
        unbindView();
    }

    /**
     * 带参数的请求数据方法
     * @param key
     */
    public void loadDataWithPara(@Nullable String key, boolean isMore, boolean isSearch) {
        action_more = isMore;
        action_search = isSearch;
        // If not load more data, need clear data.
        if (!isMore) {
            mDatas.clear();
        }
        switch (this.resType) {
            case RES_VIDEO:
                // 请求资源数据
                model.getVideoLdbListNoCache(
                        mVideoObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        key,
                        page,
                        SIZE
                );
                break;
        }

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
     * @return
     */
    private boolean checkData() {
        return mDatas !=null;
    }

    public void refreshView() {
        init2Show(AppContants.DataBase.Res.RES_VIDEO);
    }

    public void loadMore(@Nullable String key) {
        if (mDatas!= null && mDatas.size() < count) {
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

    public void init2Show(AppContants.DataBase.Res resType) {
        this.resType = resType;
        resetState();
        // load video set data
        getData();
    }

    private void getData() {
        model.getVideoListNoCache(
                mVideoObserver,
                AppHelper.getInstance().getCurrentToken(),
                videoSetID,
                null,
                page,
                SIZE
        );
    }

    public void searchData(String key) {
        resetState();
        this.action_search = true;
        this.loadDataWithPara(key, false, true);
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

    public void checkoutIntent(Intent intent) {
        if (intent == null) {
            view().showRefreshing(false);
        } else {
            videoSetID = intent.getStringExtra(AppContants.Video.SET_ID);
            view().showRefreshing(true);
        }
    }
}
