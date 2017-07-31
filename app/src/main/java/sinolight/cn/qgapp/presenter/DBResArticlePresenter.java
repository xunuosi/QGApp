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
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IDBResArticleFragmentView;

/**
 * Created by xns on 2017/7/8.
 * 资源库文章标签界面的Presenter
 */

public class DBResArticlePresenter extends BasePresenter<IDBResArticleFragmentView, HttpManager>{
    private static final String TAG = "DBResArticlePresenter";
    private static final int TYPE_ARTICLE = 0;
    private Context mContext;

    private List<KDBResData> mDatas = new ArrayList<>();
    private List<DBResArticleEntity> articleDatas;
    private CommonTitleAdapter mAdapter;

    private HttpSubscriber articleObserver = new HttpSubscriber(new OnResultCallBack<List<DBResArticleEntity>>() {

        @Override
        public void onSuccess(List<DBResArticleEntity> dbResArticleEntities) {
            if (dbResArticleEntities != null && dbResArticleEntities.size() != 0) {
                articleDatas = dbResArticleEntities;
                transformKDBResData(CommonTitleAdapter.TYPE_ARTICLE);
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "userObserver code:" + code + ",errorMsg:" + errorMsg);
            showError();
        }
    });

    private void transformKDBResData(int adapterType) {
        switch (adapterType) {
            case CommonTitleAdapter.TYPE_ARTICLE:
                mDatas.addAll(KDBResDataMapper.transformHotArticleDatas(articleDatas, adapterType, false));
                showSuccess();
                break;
        }
    }

    private void showSuccess() {
        if (mAdapter == null) {
            mAdapter = new CommonTitleAdapter(App.getContext(), mDatas);
        } else {
            mAdapter.setData(mDatas);
        }
        view().init2Show(mAdapter);

        view().showRefreshing(false);
    }

    private void showError() {
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showErrorToast(int msgId) {
        view().showErrorToast(msgId);
    }

    public DBResArticlePresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        articleObserver.unSubscribe();
        KDBResDataMapper.reset();
        mDatas.clear();
        unbindView();
    }

    public void init2Show() {
        mDatas.clear();
        insertTitle(TYPE_ARTICLE);
        getArticleData();
    }

    /**
     * Insert specify title
     * @param titleType
     */
    private void insertTitle(int titleType) {
        switch (titleType) {
            case TYPE_ARTICLE:
                mDatas.add(KDBResDataMapper.transformTitleData(
                        mContext.getString(R.string.text_hot_material),
                        CommonTitleAdapter.TYPE_ARTICLE_TITLE,
                        false));
                break;
        }

    }

    private void getArticleData() {
        model.getHotArticleWithCache(
                articleObserver,
                AppHelper.getInstance().getCurrentToken(),
                false
        );
    }

    public void refreshView() {
        init2Show();
    }
}
