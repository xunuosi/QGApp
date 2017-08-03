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
import sinolight.cn.qgapp.data.http.entity.ResArticleEntity;
import sinolight.cn.qgapp.views.activity.ReadActivity;

/**
 * Created by xns on 2017/7/6.
 * 资源文章列表不带图片的Holder
 */

public class ResArticleHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ResArticleHolder";
    public static final int TYPE_ARTICLE = 0;
    public static final int TYPE_INDUSTRY = 1;

    private int holderType;
    private ResArticleEntity data;
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


    public ResArticleHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(KDBResData<ResArticleEntity> resData,int holderType) {
        data = resData.getData();
        this.holderType = holderType;
        if (data != null) {
            bindData();
        }
    }

    private void bindData() {
        mTvItemArticleTitle.setText(data.getName());
        mTvItemArticleContent.setText(data.getAbs());
        mTvItemArticleAuthor.setText(data.getAuthor());
        mTvItemArticleSource.setText(data.getSource());
    }

    @OnClick({R.id.item_hf_article_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_hf_article_root:
                if (holderType == TYPE_ARTICLE) {
                    // Go to Article read
                    gotoReadActivity(AppContants.Read.Type.TYPE_ARTICLE);
                } else if (holderType == TYPE_INDUSTRY) {
                    gotoReadActivity(AppContants.Read.Type.TYPE_INDUSTRY);
                }
                break;
        }
    }

    private void gotoReadActivity(AppContants.Read.Type typeRead) {
        Intent callIntent = ReadActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.Read.READ_NAME, data.getName());
        callIntent.putExtra(AppContants.Read.READ_ID, data.getId());
        callIntent.putExtra(AppContants.Read.CHAPTERED_ID, "");
        callIntent.putExtra(AppContants.Read.READ_RES_TYPE, typeRead);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }
}
