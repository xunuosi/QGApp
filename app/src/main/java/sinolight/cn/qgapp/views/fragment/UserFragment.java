package sinolight.cn.qgapp.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.UserComponent;

/**
 * Created by xns on 2017/6/29.
 */

public class UserFragment extends BaseFragment {

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    public UserFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user, container, false);
        return fragmentView;
    }

    @Override
    protected UserComponent getComponent() {
        return null;
    }
}
