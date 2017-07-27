package sinolight.cn.qgapp.views.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import sinolight.cn.qgapp.dagger.component.UserComponent;

/**
 * Created by xns on 2017/6/29.
 * BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected abstract UserComponent getComponent();
}
