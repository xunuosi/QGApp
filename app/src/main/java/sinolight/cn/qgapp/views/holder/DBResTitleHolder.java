package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.DBResourceActivity;

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
                gotoAllListActivity();
                break;
        }
    }

    /**
     * 查看全部时跳转到指定的列表页面
     */
    private void gotoAllListActivity() {
        switch (typeTitle) {
            case CommonTitleAdapter.TYPE_MATERIAL_TITLE:
                break;
            case CommonTitleAdapter.TYPE_ARTICLE_TITLE:
                gotoArticleListActivity();
                break;
            case CommonTitleAdapter.TYPE_PIC_TITLE:

                break;
            case CommonTitleAdapter.TYPE_VIDEO_TITLE:
                break;
        }
    }

    private void gotoArticleListActivity() {
        Intent callIntent = DBResourceActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.DataBase.KEY_ID, "");
        callIntent.putExtra(AppContants.DataBase.KEY_RES_TYPE, AppContants.DataBase.Res.RES_ARTICLE);
        callIntent.putExtra(AppContants.DataBase.KEY_TYPE, "");
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }
}
