package sinolight.cn.qgapp.views.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
