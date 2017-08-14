package sinolight.cn.qgapp.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.data.http.entity.UserEntity;
import sinolight.cn.qgapp.presenter.UserFragmentPresenter;
import sinolight.cn.qgapp.views.activity.LoginActivity;
import sinolight.cn.qgapp.views.view.IUserFragmentView;

/**
 * Created by xns on 2017/6/29.
 * 个人中心界面
 */

public class UserFragment extends BaseFragment implements IUserFragmentView {
    @Inject
    Context mContext;
    @Inject
    UserFragmentPresenter mPresenter;
    @BindView(R.id.tv_my_center_title)
    TextView mTvMyCenterTitle;
    @BindView(R.id.iv_my_fragment_ivator)
    SimpleDraweeView mIvMyFragmentIvator;
    @BindView(R.id.tv_my_fragment_head_name)
    TextView mTvMyFragmentHeadName;
    @BindView(R.id.tb_my_center)
    Toolbar mTbMyCenter;
    Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
        mPresenter.bindView(this);
        initView();
    }

    private void initView() {
        mTvMyCenterTitle.setText(R.string.text_my_center);
    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init2Show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.clear();
    }

    @OnClick({R.id.tv_my_fragment_user_home, R.id.root_my_center_res, R.id.root_my_center_collect, R.id.root_my_center_setting, R.id.root_my_center_help, R.id.btn_my_center_change_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_fragment_user_home:
                mPresenter.gotoUserHomeActivity();
                break;
            case R.id.root_my_center_res:
                break;
            case R.id.root_my_center_collect:
                mPresenter.gotoCollectActivity();
                break;
            case R.id.root_my_center_setting:
                break;
            case R.id.root_my_center_help:
                break;
            case R.id.btn_my_center_change_account:
                this.gotoActivity(LoginActivity.getCallIntent(mContext));
                break;
        }
    }

    @Override
    public void showErrorToast(int msgId) {
        String msg = getString(msgId);
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init2Show(UserEntity userData) {
        mTvMyFragmentHeadName.setText(formatStr(R.string.text_user_name_format, userData.getUsername()));
    }

    @Override
    public void gotoActivity(Intent callIntent) {
        getActivity().startActivity(callIntent);
    }

    private String formatStr(int baseStrId, String child) {
        String local = getString(baseStrId);
        return String.format(local, child);
    }
}
