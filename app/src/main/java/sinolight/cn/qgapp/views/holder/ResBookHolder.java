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
import sinolight.cn.qgapp.data.http.entity.BookEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.KDBBookDetailActivity;

/**
 * Created by xns on 2017/7/17.
 * 知识库图书资源的布局holder
 */

public class ResBookHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ResBookHolder";
    private BookEntity mData;
    private int width;
    private int height;
    private String labelAuthor;
    private String labelTime;
    @BindView(R.id.iv_item_db_res)
    SimpleDraweeView mIvItemDbRes;
    @BindView(R.id.tv_item_db_res_title)
    TextView mTvItemDbResTitle;
    @BindView(R.id.tv_item_db_res_info)
    TextView mTvItemDbResInfo;
    @BindView(R.id.tv_item_db_res_author)
    TextView mTvItemDbResAuthor;
    @BindView(R.id.tv_item_db_res_time)
    TextView mTvItemDbResTime;
    @BindView(R.id.item_db_res_icon_root)
    ConstraintLayout mItemDbResIconRoot;

    public ResBookHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_book_icon_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_book_icon_height) /
                App.getContext().getResources().getDisplayMetrics().density);

        labelAuthor = App.getContext().getString(R.string.text_author);
        labelTime = App.getContext().getString(R.string.text_publish_time);
    }

    public void setData(KDBResData<BookEntity> data) {
        mData = data.getData();
        bindData();
    }

    private void bindData() {
        if (mData != null) {
            ImageUtil.frescoShowImageByUri(
                    App.getContext(),
                    mData.getCover(),
                    mIvItemDbRes,
                    width,
                    height
            );
            mTvItemDbResTitle.setText(mData.getName());
            mTvItemDbResInfo.setText(mData.getAbs());
            mTvItemDbResAuthor.setText(String.valueOf(labelAuthor + mData.getAuthor()));
            mTvItemDbResTime.setText(String.valueOf(labelTime + mData.getDate()));
        }
    }

    @OnClick({R.id.item_db_res_icon_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_db_res_icon_root:
                Intent callIntent = KDBBookDetailActivity.getCallIntent(App.getContext());
                callIntent.putExtra(AppContants.Resource.RES_ID, mData.getId());
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(callIntent);
                break;
        }
    }
}
