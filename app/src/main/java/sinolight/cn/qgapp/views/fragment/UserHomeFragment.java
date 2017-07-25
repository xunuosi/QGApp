package sinolight.cn.qgapp.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.data.http.entity.BookInfoEntity;
import sinolight.cn.qgapp.data.http.entity.UserEntity;
import sinolight.cn.qgapp.views.view.IUserHomeFragmentView;

/**
 * Created by xns on 2017/7/25.
 * 个人首页切换时的Fragment
 */

public class UserHomeFragment extends BaseFragment implements IUserHomeFragmentView {

    public static UserHomeFragment newInstance(int type, BookInfoEntity bookData) {
        UserHomeFragment fragment = new UserHomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(TYPE_KEY, type);
//        bundle.putParcelable(DATA_KEY, bookData);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void showErrorToast(int msgId) {

    }

    @Override
    public void init2Show(UserEntity userData) {

    }
}
