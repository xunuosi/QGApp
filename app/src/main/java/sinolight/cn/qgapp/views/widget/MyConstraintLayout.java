package sinolight.cn.qgapp.views.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.WindowInsets;

/**
 *
 * Created by xns on 2017/12/14.
 */

public class MyConstraintLayout extends ConstraintLayout {
    public MyConstraintLayout(Context context) {
        super(context);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return super.onApplyWindowInsets(
                insets.replaceSystemWindowInsets(
                       0, insets.getSystemWindowInsetTop(), 0,0));
    }
}
