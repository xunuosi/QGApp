package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
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
        MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "SearchActivity";

    @Inject
    Context mContext;
    @Inject
    SearchActivityPresenter mPresenter;

    @BindView(R2.id.tb_search)
    Toolbar mTbSearch;
    @BindView(R2.id.spinner_search)
    Spinner mSpinner;
    @BindView(R2.id.search_view)
    MaterialSearchView mSearchView;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mIdFlowlayout;


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
    protected void onStart() {
        super.onStart();
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews() {

        mSpinner.setOnItemSelectedListener(this);

        setSupportActionBar(mTbSearch);
        mTbSearch.setNavigationIcon(R.drawable.icon_back_arrow);
        mTbSearch.setNavigationOnClickListener(view -> finish());

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnSearchViewListener(this);

        mIdFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mPresenter.selectSearchTag(position);
                return true;
            }
        });

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
    public void loadSearchDataHistory(List<String> data, List<String> hotData) {
        mSearchView.setSuggestions(data.toArray(new String[data.size()]));
        initFlowTagLayout(hotData);
    }

    private void initFlowTagLayout(List<String> hotData) {
       mIdFlowlayout.setAdapter(new TagAdapter<String>(hotData) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView content = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_tag,
                        mIdFlowlayout, false);
                content.setText(s);
                return content;
            }
        });
    }

    @Override
    public void showView(ArrayAdapter<String> adapter) {
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void setSearchView(String key) {
        mSearchView.showSearch(true);
        mSearchView.setQuery(key, false);
    }

    @Override
    public void clearHisData() {
        mSearchView.setSuggestions(null);
        if (mIdFlowlayout.getAdapter() != null) {
            mIdFlowlayout.getAdapter().notifyDataChanged();
        }
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.chooseDataBase(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.iv_search_delete)
    public void onViewClicked() {
        mPresenter.deleteSearchHistory();
    }
}
