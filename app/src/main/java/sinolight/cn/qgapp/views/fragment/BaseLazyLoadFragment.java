package sinolight.cn.qgapp.views.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import sinolight.cn.qgapp.dagger.component.UserComponent;

/**
 * Created by admin on 2017/7/26.
 * 懒加载的BaseFragment
 */

public abstract class BaseLazyLoadFragment extends Fragment{
    public static final int TYPE_BAIKE_ANALYSIS = 0;
    public static final int TYPE_BAIKE_WORD = 1;

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    protected abstract UserComponent getComponent();
}
