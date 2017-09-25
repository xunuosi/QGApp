package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.AppModel;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.DataBaseBean;
import sinolight.cn.qgapp.data.db.GreenDaoHelper;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.SearchDisplayActivity;
import sinolight.cn.qgapp.views.view.ISearchActivityView;

/**
 * Created by xns on 2017/8/29.
 * Search Presenter
 */

public class SearchActivityPresenter extends BasePresenter<ISearchActivityView, HttpManager> {
    private static final String TAG = "SearchActivityPresenter";
    private Context mContext;
    private List<DataBaseBean> mDataInternet;
    private List<String> mDbNameList;
    private String dbId;
    private ArrayAdapter<String> adapter;
    private List<String> historyData;
    private List<String> hotSearchData;

    private HttpSubscriber databaseObserver = new HttpSubscriber(new OnResultCallBack<PageEntity<List<DataBaseBean>>>() {
        @Override
        public void onSuccess(PageEntity<List<DataBaseBean>> pageEntity) {
            if (pageEntity != null) {
                mDataInternet = pageEntity.getData();
            }
            mDbNameList = new ArrayList<>();
            for (DataBaseBean bean : mDataInternet) {
                mDbNameList.add(bean.getName());
            }
            showSuccess();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "databaseObserver code:" + code + ",errorMsg:" + errorMsg);
            showError(code, errorMsg);
        }
    });

    private void showError(int code, String errorMsg) {
        if (code == AppContants.SUCCESS_CODE) {
            view().showStrToast(errorMsg);
        } else {
            view().showToast(R.string.error_internet);
        }
    }

    private void showSuccess() {
        transformData();
    }

    private void transformData() {
        mDbNameList = new ArrayList<>();
        for (DataBaseBean bean : mDataInternet) {
            mDbNameList.add(bean.getName());
        }

        adapter = new ArrayAdapter<>(mContext, R.layout.spinner_layout, mDbNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view().showView(adapter);

    }

    public SearchActivityPresenter(Context context, ISearchActivityView view) {
        mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        databaseObserver.unSubscribe();
        unbindView();
    }

    public void queryData(String key) {
        // save db
        AppHelper.getInstance().saveSearchData(key);
        // goto display activity
        Intent callIntent = SearchDisplayActivity.getCallIntent(mContext);
        callIntent.putExtra(AppContants.Search.SEARCH_KEY, key);
        callIntent.putExtra(AppContants.Search.SEARCH_DB_ID, dbId);
        view().gotoActivity(callIntent);
    }

    public void init2Show() {
        loadSearchHistory();
        loadDataBase();
    }

    private void loadDataBase() {
        model.getKDBWithCache(
                databaseObserver,
                AppHelper.getInstance().getCurrentToken(),
                1,
                10,
                false);
    }

    public void loadSearchHistory() {
        // load search history
        historyData = AppHelper.getInstance().getSearchDatas();
        Collections.reverse(historyData);
        hotSearchData = new ArrayList<>();
        for (int i = 0; i < historyData.size() && i < 10; i++) {
            hotSearchData.add(historyData.get(i));
        }
        if (historyData != null && !historyData.isEmpty()) {
            view().loadSearchDataHistory(historyData, hotSearchData);
        }
    }

    public void chooseDataBase(int dbIndex) {
        if (mDataInternet != null) {
            dbId = mDataInternet.get(dbIndex).getId();
        }
    }

    public void selectSearchTag(int position) {
        if (position >= 0 && position < historyData.size() - 1) {
            view().setSearchView(historyData.get(position));
        }
    }

    public void deleteSearchHistory() {
        // clear database
        GreenDaoHelper.getDaoSession().getSearchDataDao().deleteAll();
        // clear memory
        if (historyData != null) {
            historyData.clear();
        }
        if (hotSearchData != null) {
            hotSearchData.clear();
        }
        // clear view
        view().clearHisData();
        view().showToast(R.string.text_clear_search_history_data_success);
    }
}
