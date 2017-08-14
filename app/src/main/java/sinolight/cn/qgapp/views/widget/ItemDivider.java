package sinolight.cn.qgapp.views.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/8/14.
 * Item divider util
 */

public class ItemDivider extends Y_DividerItemDecoration {
    private Context context;

    public ItemDivider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        divider = new Y_DividerBuilder()
                .setLeftSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                .setBottomSideLine(true, ContextCompat.getColor(context, R.color.color_bottom_divider), 0.5f, 0, 0)
                .setRightSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                .setTopSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                .create();
        return divider;
    }
}

