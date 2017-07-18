package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.ResArticleEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.L;

/**
 * Created by xns on 2017/7/6.
 * 资源文章列表带图片的Holder
 */

public class ResArticleWithPicHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ResArticleWithPicHolder";
    private ResArticleEntity data;
    private int width;
    private int height;

    @BindView(R.id.tv_item_icon_article_title)
    TextView mTvItemIconArticleTitle;
    @BindView(R.id.tv_item_icon_article_content)
    TextView mTvItemIconArticleContent;
    @BindView(R.id.tv_item_icon_article_author)
    TextView mTvItemIconArticleAuthor;
    @BindView(R.id.tv_item_icon_article_source)
    TextView mTvItemIconArticleSource;
    @BindView(R.id.iv_item_icon_article)
    SimpleDraweeView mIvItemIconArticle;
    @BindView(R.id.item_icon_article_root)
    ConstraintLayout mItemIconArticleRoot;

    public ResArticleWithPicHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);

        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_article_icon_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_article_icon_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(KDBResData<ResArticleEntity> resData) {
        data = resData.getData();
        bindData();
    }

    private void bindData() {
        mTvItemIconArticleTitle.setText(data.getName());
        mTvItemIconArticleContent.setText(data.getAbs());
        mTvItemIconArticleAuthor.setText(data.getAuthor());
        mTvItemIconArticleSource.setText(data.getSource());

        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                data.getCover(),
                mIvItemIconArticle,
                width,
                height
        );
    }

    @OnClick({R.id.item_icon_article_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_icon_article_root:
                L.d(TAG, "onClick:" + data.getName());
                break;
        }
    }
}
