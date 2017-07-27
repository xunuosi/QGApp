package sinolight.cn.qgapp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.MaterialEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IDBResMaterialFragmentView;

/**
 * Created by xns on 2017/7/8.
 * 资源库素材标签界面的Presenter
 */

public class DBResMaterialPresenter extends BasePresenter<IDBResMaterialFragmentView, HttpManager>{
    private static final String TAG = "DBResMaterialPresenter";
    private static final int TYPE_MATERIAL = 0;
    private static final int TYPE_ARTICLE = 1;

    private Context mContext;
    private List<KDBResData> mDatas = new ArrayList<>();
    private List<MaterialEntity> materialDatas;
    private List<DBResArticleEntity> articelDatas;
    private CommonTitleAdapter mAdapter;

    private HttpSubscriber materialObserver = new HttpSubscriber(new OnResultCallBack<List<MaterialEntity>>() {

        @Override
        public void onSuccess(List<MaterialEntity> materialEntities) {
            if (materialEntities != null && materialEntities.size() != 0) {
                materialDatas = materialEntities;
                transformKDBResData(CommonTitleAdapter.TYPE_MATERIAL);
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "userObserver code:" + code + ",errorMsg:" + errorMsg);
            showError();
        }
    });

    private HttpSubscriber articleObserver = new HttpSubscriber(new OnResultCallBack<List<DBResArticleEntity>>() {

        @Override
        public void onSuccess(List<DBResArticleEntity> dbResArticleEntities) {
            if (dbResArticleEntities != null && dbResArticleEntities.size() != 0) {
                articelDatas = dbResArticleEntities;
                transformKDBResData(CommonTitleAdapter.TYPE_ARTICLE);
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "userObserver code:" + code + ",errorMsg:" + errorMsg);
            showError();
        }
    });

    /**
     * Insert specify title
     * @param titleType
     */
    private void insertTitle(int titleType) {
        switch (titleType) {
            case TYPE_MATERIAL:
                mDatas.add(KDBResDataMapper.transformTitleData(
                        mContext.getString(R.string.text_hot_material),
                        CommonTitleAdapter.TYPE_MATERIAL_TITLE,
                        false));
                break;
            case TYPE_ARTICLE:
                mDatas.add(KDBResDataMapper.transformTitleData(
                        mContext.getString(R.string.text_hot_article),
                        CommonTitleAdapter.TYPE_ARTICLE_TITLE,
                        false));
                getHotArticle();
                break;
        }

    }

    private void transformKDBResData(int adapterType) {
        switch (adapterType) {
            case CommonTitleAdapter.TYPE_MATERIAL:
                mDatas.addAll(KDBResDataMapper.transformMaterialDatas(materialDatas, adapterType, false));
                insertTitle(TYPE_ARTICLE);
                break;
            case CommonTitleAdapter.TYPE_ARTICLE:
                mDatas.addAll(KDBResDataMapper.transformHotArticleDatas(articelDatas, adapterType, false));
                showSuccess();
                break;
        }
    }

    private void getHotArticle() {
        model.getHotArticleWithCache(
                articleObserver,
                AppHelper.getInstance().getCurrentToken(),
                false
        );
    }

    private void showSuccess() {
        if (mAdapter == null) {
            mAdapter = new CommonTitleAdapter(App.getContext(), mDatas);
        } else {
            mAdapter.setData(mDatas);
        }
        view().init2Show(mAdapter);
    }

    private void showError() {
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showErrorToast(int msgId) {
        view().showErrorToast(msgId);
    }

    public DBResMaterialPresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        materialObserver.unSubscribe();
        KDBResDataMapper.reset();
        mDatas.clear();
        unbindView();
    }

    public void init2Show() {
        mDatas.clear();
        insertTitle(TYPE_MATERIAL);
        model.getHotMenuWithCache(
                materialObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

}
