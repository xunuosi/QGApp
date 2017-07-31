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
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IDBResVideoFragmentView;

/**
 * Created by xns on 2017/7/8.
 * 资源库视频标签界面的Presenter
 */

public class DBResVideoPresenter extends BasePresenter<IDBResVideoFragmentView, HttpManager>{
    private static final String TAG = "DBResVideoPresenter";
    private static final int TYPE_VIDEO = 0;

    private Context mContext;
    private List<KDBResData> mDatas = new ArrayList<>();
    private List<DBResVideoEntity> videoDatas;
    private CommonTitleAdapter mAdapter;

    private HttpSubscriber videoObserver = new HttpSubscriber(new OnResultCallBack<List<DBResVideoEntity>>() {

        @Override
        public void onSuccess(List<DBResVideoEntity> entities) {
            if (entities != null && entities.size() != 0) {
                videoDatas = entities;
                transformKDBResData(CommonTitleAdapter.TYPE_VIDEO);
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "Observer code:" + code + ",errorMsg:" + errorMsg);
            showError();
        }
    });

    /**
     * Insert specify title
     * @param titleType
     */
    private void insertTitle(int titleType) {
        switch (titleType) {
            case TYPE_VIDEO:
                mDatas.add(KDBResDataMapper.transformTitleData(
                        mContext.getString(R.string.text_hot_video),
                        CommonTitleAdapter.TYPE_VIDEO_TITLE,
                        false));
                break;
        }

    }

    private void transformKDBResData(int adapterType) {
        switch (adapterType) {
            case CommonTitleAdapter.TYPE_VIDEO:
                mDatas.addAll(KDBResDataMapper.transformVideoDatas(videoDatas, adapterType, false));
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
        view().showRefreshing(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showErrorToast(int msgId) {
        view().showErrorToast(msgId);
    }

    public DBResVideoPresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        videoObserver.unSubscribe();
        KDBResDataMapper.reset();
        mDatas.clear();
        unbindView();
    }

    public void init2Show() {
        mDatas.clear();
        insertTitle(TYPE_VIDEO);
        model.getHotVideoWithCache(
                videoObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

}
