package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
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
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.DBResPicEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.DBResourceActivity;
import sinolight.cn.qgapp.views.activity.ResPicListActivity;

/**
 * Created by xns on 2017/7/17.
 * 资源库图片holder
 */

public class DBResPicHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "DBResMaterialHolder";
    @BindView(R.id.iv_resdb_pic)
    SimpleDraweeView mIvResdbPic;
    @BindView(R.id.tv_resdb_pic_title)
    TextView mTvResdbPicTitle;
    @BindView(R.id.tv_resdb_pic_info)
    TextView mTvResdbPicInfo;
    @BindView(R.id.tv_resdb_pic_from)
    TextView mTvResdbPicFrom;
    @BindView(R.id.root_db_res_hot_pic)
    ConstraintLayout mRootDbResHotPic;
    private DBResPicEntity mData;
    private int width;
    private int height;

    public DBResPicHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.resdb_materail_cover_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.resdb_materail_cover_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(KDBResData<DBResPicEntity> data) {
        if (data != null) {
            mData = data.getData();
            bindData();
        }
    }

    private void bindData() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                mData.getCover(),
                mIvResdbPic,
                width,
                height
        );

        mTvResdbPicTitle.setText(mData.getName());
        mTvResdbPicInfo.setText(mData.getAbs());
        mTvResdbPicFrom.setText(formatStr(R.string.text_from_format, mData.getSource()));
    }

    private String formatStr(int baseStrId, String child) {
        String local = App.getContext().getString(baseStrId);
        return String.format(local, child);
    }

    @OnClick({R.id.root_db_res_hot_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root_db_res_hot_pic:
                gotoPicListActivity();
                break;
        }
    }

    private void gotoPicListActivity() {
        Intent callIntent = ResPicListActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.Resource.RES_ID, mData.getId());
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }
}
