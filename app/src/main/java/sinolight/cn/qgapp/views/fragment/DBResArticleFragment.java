package sinolight.cn.qgapp.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.UserComponent;

/**
 * Created by xns on 2017/7/26.
 * 热门素材Fragment
 */

public class DBResArticleFragment extends BaseFragment {

    public static DBResArticleFragment newInstance() {
        return new DBResArticleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_res_db_articlel, container, false);

        return fragmentView;
    }

    @Override
    protected UserComponent getComponent() {
        return null;
    }
}
