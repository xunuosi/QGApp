package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.ChapterEntity;
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
    private final Context mContext;
    private String readID;
    private String readName;
    private AppContants.Read.Type readType;
    private KDBResAdapter mAdapter;
    private List<ChapterEntity> chapterDatas;
    private List<KDBResData> mDatas;

    private HttpSubscriber<List<ChapterEntity>> chapterObserver = new HttpSubscriber<>(
            new OnResultCallBack<List<ChapterEntity>>() {

                @Override
                public void onSuccess(List<ChapterEntity> chapterEntities) {
                    if (chapterEntities != null) {
                        chapterDatas = chapterEntities;
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
                list = KDBResDataMapper.transformChapterDatas(chapterDatas, KDBResAdapter.TYPE_CHAPTER, false);
                break;
        }

        mDatas = list;
        showSuccess();
    }

    private void showSuccess() {
        if (mAdapter == null) {
            mAdapter = new KDBResAdapter(mContext, mDatas);
        } else {
            mAdapter.setData(mDatas);
        }
        view().showListView(mAdapter, readName);
    }

    private void showError(int code, String errorMsg) {
        view().showToastStr(errorMsg);
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
            getData();
        }
    }

    private void getData() {
        model.getCatalogListNoCache(
                chapterObserver,
                AppHelper.getInstance().getCurrentToken(),
                readID,
                readType.getType()
        );
    }
}
