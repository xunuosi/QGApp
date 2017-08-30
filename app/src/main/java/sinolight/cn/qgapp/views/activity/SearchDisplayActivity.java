package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerSearchDisplayActivityComponent;
import sinolight.cn.qgapp.dagger.module.SearchDisplayActivityModule;
import sinolight.cn.qgapp.presenter.SearchDisplayActivityPresenter;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.ISearchDisplayActivityView;

/**
 * Created by xns on 2017/8/29.
 * Search Activity
 */

public class SearchDisplayActivity extends BaseActivity implements ISearchDisplayActivityView,
        MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener {
    private static final String TAG = "SearchDisplayActivity";
    @Inject
    Context mContext;
    @Inject
    SearchDisplayActivityPresenter mPresenter;
    @Inject
    ArrayAdapter<CharSequence> spinnerAdapter;

    @BindView(R2.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R2.id.tb_search_dis)
    Toolbar mTbSearch;
    @BindView(R2.id.spinner_search_dis)
    Spinner mSpinner;
    @BindView(R2.id.search_view)
    MaterialSearchView mSearchView;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, SearchDisplayActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.init2Show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_search_display;
    }

    @Override
    protected void initViews() {
        mSpinner.setAdapter(spinnerAdapter);

        setSupportActionBar(mTbSearch);
        mTbSearch.setNavigationIcon(R.drawable.icon_back_arrow);

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnSearchViewListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerSearchDisplayActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .searchDisplayActivityModule(new SearchDisplayActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showStrToast(msg);
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showLoading(boolean enable) {
        if (enable) {
            mLoadingRoot.setVisibility(View.VISIBLE);
        } else {
            mLoadingRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadSearchDataHistory(List<String> data) {
        mSearchView.setSuggestions(data.toArray(new String[data.size()]));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        L.d(TAG, "onQueryTextSubmit");
        mPresenter.queryData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSearchViewShown() {

    }

    @Override
    public void onSearchViewClosed() {


    }

}
