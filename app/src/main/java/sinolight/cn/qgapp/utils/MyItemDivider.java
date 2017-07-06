package sinolight.cn.qgapp.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import sinolight.cn.qgapp.R;

/**
 * Created by admin on 2017/7/6.
 * RecyclerView Item的分割线
 */

public class MyItemDivider extends Y_DividerItemDecoration {
    private Context context;

    public MyItemDivider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        switch (itemPosition) {
            case 0:
                divider = new Y_DividerBuilder()
                        .setBottomSideLine(true, ContextCompat.getColor(context, R.color.color_bottom_divider), 0.5f, 0, 0)
                        .create();
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                divider = new Y_DividerBuilder()
                        .setLeftSideLine(true, ContextCompat.getColor(context, R.color.color_bottom_divider), 0.5f, 0, 0)
                        .setBottomSideLine(true, ContextCompat.getColor(context, R.color.color_bottom_divider), 0.5f, 0, 0)
                        .setRightSideLine(true, ContextCompat.getColor(context, R.color.color_bottom_divider), 0.5f, 0, 0)
                        .setTopSideLine(true, ContextCompat.getColor(context, R.color.color_bottom_divider), 0.5f, 0, 0)
                        .create();
                break;
            default:
                break;
        }
        return divider;
    }
}