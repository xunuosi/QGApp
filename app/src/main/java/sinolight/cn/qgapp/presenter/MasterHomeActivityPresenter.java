package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MasterAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.MasterEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IMasterHomeActivityView;

/**
 * Created by xns on 2017/8/3.
 * ChapterActivity presenter层
 */

public class MasterHomeActivityPresenter extends BasePresenter<IMasterHomeActivityView, HttpManager> {
    private static final String TAG = "MasterHomeActivityPresenter";
    private static final int TYPE_TOP_BANNER = 0;
    private static final int TYPE_MASTER_LIST = 1;

    private final Context mContext;
    private MasterAdapter mAdapter;
    private MasterEntity mTopMaster;
    private List<MasterEntity> masterList;
    private List<KDBResData> mDatas = new ArrayList<>();

    private HttpSubscriber<MasterEntity> topObserver = new HttpSubscriber<>(
            new OnResultCallBack<MasterEntity>() {

                @Override
                public void onSuccess(MasterEntity masterEntity) {
                    if (masterEntity != null) {
                        mTopMaster = masterEntity;
                        showSuccess(TYPE_TOP_BANNER);
                    } else {
                        showError(0, "数据未获取成功!");
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "chapterObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError(code, errorMsg);
                }
            });

    private HttpSubscriber<List<MasterEntity>> masterListObserver = new HttpSubscriber<>(
            new OnResultCallBack<List<MasterEntity>>() {

                @Override
                public void onSuccess(List<MasterEntity> masterEntities) {
                    if (masterEntities != null) {
                        masterList = masterEntities;
                        showSuccess(TYPE_MASTER_LIST);
                    } else {
                        showError(0, "数据未获取成功!");
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "chapterObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError(code, errorMsg);
                }
            });

    public MasterHomeActivityPresenter(Context context, IMasterHomeActivityView view) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    private void transformKDBResData() {
        List<KDBResData> list = new ArrayList<>();
        list = KDBResDataMapper.transformMasterDatas(masterList, MasterAdapter.TYPE_MASTER_LIST, false);
        mDatas = list;

        showListView();
    }

    private void showListView() {
        if (mAdapter == null) {
            mAdapter = new MasterAdapter(mContext, mDatas);
        } else {
            mAdapter.setData(mDatas);
        }
        view().showListView(mAdapter);

        closeRefreshView();
    }

    private void showSuccess(int type) {
        switch (type) {
            case TYPE_TOP_BANNER:
                showTopMaster();
                break;
            case TYPE_MASTER_LIST:
                transformKDBResData();
                break;
        }
    }

    private void showTopMaster() {
        if (mTopMaster.getCover() != null) {
            view().showTopBanner(mTopMaster.getCover());
        }
        closeRefreshView();
    }

    private void closeRefreshView() {
        if (checkData()) {
            view().showRefreshing(false);
        }
    }

    private boolean checkData() {
        return mTopMaster != null && masterList != null && masterList.size() != 0;
    }

    private void showError(int code, String errorMsg) {
        view().showToastStr(errorMsg);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        topObserver.unSubscribe();
        mDatas.clear();
        KDBResDataMapper.reset();
        unbindView();
    }


    private void getData() {
        // Get TopMaster
        model.getMasterTopWithCache(
                topObserver,
                AppHelper.getInstance().getCurrentToken(),
                null,
                false
        );

        // Get MasterList
        model.getMasterHotListWithCache(
                masterListObserver,
                AppHelper.getInstance().getCurrentToken(),
                null,
                true
        );
    }

    public void init2Show() {
        resetState();
        getData();
    }

    @Override
    protected void resetState() {
        super.resetState();
        mDatas.clear();
    }

    /**
     *
     * @param key
     */
    public void searchData(String key) {
        if (checkKey(key)) {
            view().showToast(R.string.text_search_data_empty);
        } else {
            // hide keyboard
            view().hideKeyboard(true);
            getSearchData(key);
        }
    }

    private void getSearchData(String key) {
        model.getMasterHotListWithCache(
                masterListObserver,
                AppHelper.getInstance().getCurrentToken(),
                key,
                true
        );
    }

    private boolean checkKey(String key) {
        return TextUtils.isEmpty(key);
    }
}
