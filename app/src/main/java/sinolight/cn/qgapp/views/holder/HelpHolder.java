package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.data.http.entity.HelpDataEntity;

/**
 * Created by xns on 2017/7/6.
 * 九大行业数据库
 */

public class HelpHolder extends RecyclerView.ViewHolder {
    @BindView(R2.id.iv_message)
    SimpleDraweeView mIvMessage;
    @BindView(R2.id.tv_item_help_title)
    TextView mTvTitle;
    @BindView(R2.id.iv_help_arrow)
    SimpleDraweeView mIvHelpArrow;
    @BindView(R2.id.root_item_help)
    ConstraintLayout mRootItemHelp;
    private HelpDataEntity bean;

    public HelpHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(HelpDataEntity bean) {
        this.bean = bean;
        if (bean != null) {
            bindData();
        }
    }

    private void bindData() {
        mTvTitle.setText(bean.getTitle());
    }

    @OnClick(R.id.root_item_help)
    public void onViewClicked() {

    }
}
