package sinolight.cn.qgapp.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.data.http.entity.UserEntity;
import sinolight.cn.qgapp.presenter.UserHomeFragmentPresenter;
import sinolight.cn.qgapp.views.view.IUserHomeFragmentView;

/**
 * Created by xns on 2017/7/25.
 * 个人首页切换时的Fragment
 */

public class UserHomeFragment extends BaseFragment implements IUserHomeFragmentView {
    private static final String TYPE_KEY = "sinolight.cn.qgapp.views.fragment.UserHomeFragment_TYPE";
    private static final String DATA_KEY = "sinolight.cn.qgapp.views.fragment.UserHomeFragment_DATA";
    public static final int TYPE_PERSONINFO = 0;
    public static final int TYPE_SECURITY = 1;

    @Inject
    UserHomeFragmentPresenter mPresenter;

    private EditText mEtUserHomeOriginPw;
    private EditText mEtUserHomeNewPw;
    private EditText mEtUserConfirmNewPw;
    private Button mBtnUserHomeSubmit;

    private TextView mTvUserHomeName;
    private TextView mTvUserHomePhone;
    private TextView mTvUserHomeEmail;
    private int mType;
    private UserEntity mUserData;

    public static UserHomeFragment newInstance(int type, UserEntity userData) {
        UserHomeFragment fragment = new UserHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        bundle.putParcelable(DATA_KEY, userData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(TYPE_KEY, 0);
        mUserData = getArguments().getParcelable(DATA_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = 0;
        switch (mType) {
            case TYPE_PERSONINFO:
                layoutId = R.layout.fragment_user_home_info;
                break;
            case TYPE_SECURITY:
                layoutId = R.layout.fragment_user_home_security;
                break;
        }
        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (mType) {
            case TYPE_PERSONINFO:
                bindInfo(view);
                break;
            case TYPE_SECURITY:
                bindSecurity(view);
                break;
        }
    }

    private void bindSecurity(View view) {
        mEtUserHomeOriginPw = view.findViewById(R.id.et_user_home_origin_pw);
        mEtUserHomeNewPw = view.findViewById(R.id.et_user_home_new_pw);
        mEtUserConfirmNewPw = view.findViewById(R.id.et_user_confirm_new_pw);

        mBtnUserHomeSubmit = view.findViewById(R.id.btn_user_home_submit);
        mBtnUserHomeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.changePW(
                        mEtUserHomeOriginPw.getText().toString().trim(),
                        mEtUserHomeNewPw.getText().toString().trim(),
                        mEtUserConfirmNewPw.getText().toString().trim()
                );
            }
        });
    }

    private void bindInfo(View view) {
        mTvUserHomeName = view.findViewById(R.id.tv_user_home_name);
        mTvUserHomePhone = view.findViewById(R.id.tv_user_home_phone);
        mTvUserHomeEmail = view.findViewById(R.id.tv_user_home_email);

        mTvUserHomeName.setText(mUserData.getUsername());
        mTvUserHomePhone.setText(mUserData.getPhone());
        mTvUserHomeEmail.setText(mUserData.getEmail());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
        if (mType == TYPE_SECURITY) {
            mPresenter.bindView(this);
        }
    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void showErrorToast(int msgId) {
        String msg = getActivity().getString(msgId);
        this.showErrorToast(msg);
    }

    @Override
    public void showErrorToast(String msg) {
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init2Show(UserEntity userData) {

    }

    @Override
    public void clear() {
        mTvUserHomeName.setText(null);
        mTvUserHomePhone.setText(null);
        mTvUserHomeEmail.setText(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.clear();
    }
}
