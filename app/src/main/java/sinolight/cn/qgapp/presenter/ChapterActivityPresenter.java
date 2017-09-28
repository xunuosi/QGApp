package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.ChapterEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IChapterActivityView;

/**
 * Created by xns on 2017/8/3.
 * ChapterActivity presenter层
 */

public class ChapterActivityPresenter extends BasePresenter<IChapterActivityView, HttpManager> {
    private static final String TAG = "ChapterActivityPresenter";
    private static int ACTION_INIT = 0;
    private static int ACTION_LOAD_MORE = 1;
    private static final int SIZE = 10;
    private int action;
    private int page = 1;
    // 获取当前资源列表的总数
    private int count = 0;
    private final Context mContext;
    private String readID;
    private String readName;
    private AppContants.Read.Type readType;
    private KDBResAdapter mAdapter;
    private List<ChapterEntity> chapterDatas;
    private List<KDBResData> mDatas = new ArrayList<>();

    private HttpSubscriber<PageEntity<List<ChapterEntity>>> chapterObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<ChapterEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<ChapterEntity>> pageEntity) {
                    if (pageEntity != null) {
                        count = pageEntity.getCount();
                        chapterDatas = pageEntity.getData();
                        transformKDBResData(AppContants.Read.Type.TYPE_BOOK);
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

    public ChapterActivityPresenter(Context context, IChapterActivityView view) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    private void transformKDBResData(AppContants.Read.Type resType) {
        List<KDBResData> list = new ArrayList<>();
        switch (resType) {
            case TYPE_BOOK:
            case TYPE_STAND:
                list = KDBResDataMapper.transformChapterDatas(chapterDatas, readID, KDBResAdapter.TYPE_CHAPTER, false);
                break;
        }
        if (action == ACTION_INIT) {
            mDatas.clear();
        }
        mDatas.addAll(list);
        showSuccess();
    }

    private void showSuccess() {
        showLoadView(false);
        if (mAdapter == null) {
            mAdapter = new KDBResAdapter(mContext, mDatas);
        } else {
            mAdapter.setData(mDatas);
        }
        view().showListView(mAdapter, readName);
    }

    private void showError(int code, String errorMsg) {
        showLoadView(false);
        view().showToastStr(errorMsg);
    }

    private void showLoadView(boolean enable) {
        if (action == ACTION_INIT) {
            view().showRefreshing(enable);
        } else {
            view().showLoadMoreing(enable);
        }
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        chapterObserver.unSubscribe();
        KDBResDataMapper.reset();
        unbindView();
    }

    public void checkIntent(Intent intent) {
        if (intent != null) {
            readID = intent.getStringExtra(AppContants.Read.READ_ID);
            readName = intent.getStringExtra(AppContants.Read.READ_NAME);
            readType = (AppContants.Read.Type) intent.getSerializableExtra(AppContants.Read.READ_RES_TYPE);
            view().showRefreshing(true);
        }
    }

    private void getData(int action) {
        this.action = action;
        model.getCatalogListNoCache(
                chapterObserver,
                AppHelper.getInstance().getCurrentToken(),
                readID,
                readType.getType(),
                page,
                SIZE
        );
    }

    public void refreshData() {
        resetState();
        getData(ACTION_INIT);
    }

    @Override
    protected void resetState() {
        super.resetState();
        page = 1;
        view().hasMoreData(true);
    }

    public void loadMore() {
        if (checkLoadMoreState()) {
            page++;
            getData(ACTION_LOAD_MORE);
        } else {
            view().hasMoreData(false);
        }
    }

    private boolean checkLoadMoreState() {
        return mDatas.size() < count;
    }
}
