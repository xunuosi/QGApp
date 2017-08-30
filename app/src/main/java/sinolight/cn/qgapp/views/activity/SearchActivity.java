package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerSearchActivityComponent;
import sinolight.cn.qgapp.dagger.module.SearchActivityModule;
import sinolight.cn.qgapp.presenter.SearchActivityPresenter;
import sinolight.cn.qgapp.views.view.ISearchActivityView;

/**
 * Created by xns on 2017/8/29.
 * Search Activity
 */

public class SearchActivity extends BaseActivity implements ISearchActivityView,
        MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener {
    private static final String TAG = "SearchActivity";
    @Inject
    Context mContext;
    @Inject
    SearchActivityPresenter mPresenter;
    @Inject
    ArrayAdapter<CharSequence> spinnerAdapter;

    @BindView(R2.id.tb_search)
    Toolbar mTbSearch;
    @BindView(R2.id.spinner_search)
    Spinner mSpinner;
    @BindView(R2.id.search_view)
    MaterialSearchView mSearchView;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, SearchActivity.class);
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
        return R.layout.activity_search;
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
        DaggerSearchActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .searchActivityModule(new SearchActivityModule(this))
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
    public void loadSearchDataHistory(List<String> data) {
        mSearchView.setSuggestions(data.toArray(new String[data.size()]));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
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
