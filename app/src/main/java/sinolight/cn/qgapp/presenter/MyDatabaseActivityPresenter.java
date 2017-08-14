package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KnowledgeAdapter;
import sinolight.cn.qgapp.data.bean.DataBaseBean;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IKnowledgeFragmentView;
import sinolight.cn.qgapp.views.view.IMyDatabaseActivityView;

/**
 * Created by xns on 2017/7/8.
 * 知识库界面的Presenter
 */

public class MyDatabaseActivityPresenter extends BasePresenter<IMyDatabaseActivityView, HttpManager>{
    private static final String TAG = "MyDatabaseActivityPresenter";
    private Context mContext;
    private List<DataBaseBean> mDataInternet;
    private KnowledgeAdapter mKnowledgeAdapter;

    private HttpSubscriber databaseObserver = new HttpSubscriber(new OnResultCallBack<PageEntity<List<DataBaseBean>>>() {
        @Override
        public void onSuccess(PageEntity<List<DataBaseBean>> pageEntity) {
            if (pageEntity != null) {
                mDataInternet = pageEntity.getData();
            }
            if (mDataInternet == null || mDataInternet.isEmpty()) {
                Toast.makeText(mContext, mContext.getString(R.string.attention_data_refresh_error), Toast.LENGTH_SHORT).show();
            } else {
                // 显示获取数据
                closeLoading();
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "databaseObserver code:" + code + ",errorMsg:" + errorMsg);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            mDataInternet = new ArrayList<>();
            closeLoading();
        }
    });

    public MyDatabaseActivityPresenter(Context context, IMyDatabaseActivityView view) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    private void closeLoading() {
        if (checkData() && view() != null) {
            mKnowledgeAdapter = new KnowledgeAdapter(mContext, mDataInternet);

        }
    }

    private boolean checkData() {
        return mDataInternet != null;
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        databaseObserver.unSubscribe();
        unbindView();
    }

    public void init2Show() {
        model.getKDBWithCache(
                databaseObserver,
                AppHelper.getInstance().getCurrentToken(),
                1,
                10,
                false);
    }
}
