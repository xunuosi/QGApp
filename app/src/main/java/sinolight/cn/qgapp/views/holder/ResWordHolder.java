package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.ResWordEntity;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.KDBDicDetailActivity;

/**
 * Created by xns on 2017/7/19.
 * 词条资源holder
 */

public class ResWordHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ResWordHolder";
    private ResWordEntity mData;
    private String indicator;
    @BindView(R.id.tv_db_res_word_indicator)
    TextView mTvDbResWordIndicator;
    @BindView(R.id.tv_db_res_word_title)
    TextView mTvDbResWordTitle;
    @BindView(R.id.tv_db_res_word_info)
    TextView mTvDbResWordInfo;
    @BindView(R.id.root_db_res_dic)
    ConstraintLayout mRootDbResDic;


    public ResWordHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(KDBResData<ResWordEntity> data, int position) {
        mData = data.getData();
        if (mData != null) {
            indicator = String.valueOf(position + 1);
            bindData();
        }
    }

    private void bindData() {
        mTvDbResWordIndicator.setText(indicator);
        mTvDbResWordTitle.setText(mData.getName());
        mTvDbResWordInfo.setText(String.format(
                App.getContext().getString(R.string.text_resource_format), mData.getSource())
        );

    }

    @OnClick({R.id.root_db_res_dic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root_db_res_dic:
                Intent callIntent = KDBDicDetailActivity.getCallIntent(App.getContext());
                callIntent.putExtra(AppContants.Resource.RES_ID, mData.getId());
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(callIntent);
                break;
        }
    }
}
