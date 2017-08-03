package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.ChapterEntity;

/**
 * Created by xns on 2017/7/17.
 * 文章章节目录holder
 */

public class ChapterHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ChapterHolder";
    @BindView(R.id.tv_item_chapter)
    TextView mTvItemChapter;
    @BindView(R.id.item_root_chapter)
    ConstraintLayout mItemRootChapter;
    private ChapterEntity mData;

    public ChapterHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(KDBResData<ChapterEntity> data) {
        if (data != null) {
            mData = data.getData();
            bindData();
        }
    }

    private void bindData() {
        mTvItemChapter.setText(mData.getName());
    }

    @OnClick({R.id.item_root_chapter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_root_chapter:
                // Go to Article read
                gotoReadActivity();
                break;
        }
    }

    private void gotoReadActivity() {
//        Intent callIntent = ReadActivity.getCallIntent(App.getContext());
//        callIntent.putExtra(AppContants.Read.READ_NAME, mData.getName());
//        callIntent.putExtra(AppContants.Read.READ_ID, mData.getId());
//        callIntent.putExtra(AppContants.Read.CHAPTERED_ID, "");
//        callIntent.putExtra(AppContants.Read.READ_RES_TYPE, AppContants.Read.Type.TYPE_ARTICLE);
//        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        App.getContext().startActivity(callIntent);
    }
}
