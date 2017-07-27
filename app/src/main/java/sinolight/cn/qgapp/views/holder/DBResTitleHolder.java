package sinolight.cn.qgapp.views.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.utils.L;

/**
 * Created by xns on 2017/7/17.
 * 资源库标题holder
 */

public class DBResTitleHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "DBResTitleHolder";
    @BindView(R.id.tv_item_dbres_ct_title)
    TextView mTvItemDbresCtTitle;
    @BindView(R.id.tv_item_dbres_ct_get)
    TextView mTvItemDbresCtGet;
    @BindView(R.id.iv_item_dbres_ct_indicator2)
    SimpleDraweeView mIvItemDbresCtIndicator2;
    private String titleName;
    private int typeTitle;

    public DBResTitleHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(KDBResData<String> data, int typeTitle) {
        if (data != null) {
            titleName = data.getData();
            this.typeTitle = typeTitle;
            bindData();
        }
    }

    private void bindData() {
        mTvItemDbresCtTitle.setText(titleName);
    }

    @OnClick({R.id.tv_item_dbres_ct_get, R.id.iv_item_dbres_ct_indicator2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_item_dbres_ct_get:
            case R.id.iv_item_dbres_ct_indicator2:
                L.d(TAG, "onClick:" + titleName);
                break;
        }
    }
}
