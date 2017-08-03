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
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.views.activity.ReadActivity;

/**
 * Created by xns on 2017/7/6.
 * 热门文章
 */

public class ArticleHolder extends RecyclerView.ViewHolder {
    private HomeData mHomeData;
    @BindView(R.id.tv_item_article_title)
    TextView mTvItemArticleTitle;
    @BindView(R.id.tv_item_article_content)
    TextView mTvItemArticleContent;
    @BindView(R.id.tv_item_article_author)
    TextView mTvItemArticleAuthor;
    @BindView(R.id.tv_item_article_source)
    TextView mTvItemArticleSource;
    @BindView(R.id.item_hf_article_root)
    ConstraintLayout mItemHfArticleRoot;


    public ArticleHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(HomeData homeData) {
        mHomeData = homeData;
        if (mHomeData.getId() != null) {
            bindData();
        }
    }

    private void bindData() {
        mTvItemArticleTitle.setText(mHomeData.getTitle());
        mTvItemArticleContent.setText(mHomeData.getRemark());
        mTvItemArticleAuthor.setText(mHomeData.getAuthor());
        mTvItemArticleSource.setText(mHomeData.getSource());
    }

    @OnClick({R.id.item_hf_article_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_hf_article_root:
                // Go to Article read
                gotoReadActivity();
                break;
        }
    }

    private void gotoReadActivity() {
        Intent callIntent = ReadActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.Read.READ_NAME, mHomeData.getTitle());
        callIntent.putExtra(AppContants.Read.READ_ID, mHomeData.getId());
        callIntent.putExtra(AppContants.Read.CHAPTERED_ID, "");
        callIntent.putExtra(AppContants.Read.READ_RES_TYPE, AppContants.Read.Type.TYPE_ARTICLE);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }
}
