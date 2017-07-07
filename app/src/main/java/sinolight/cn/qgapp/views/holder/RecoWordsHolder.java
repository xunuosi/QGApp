package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.data.http.entity.RecommendEntity;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.widget.FrescoLoader;

/**
 * Created by xns on 2017/7/5.
 * 首页顶部轮播图片
 */

public class RecoWordsHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "RecoWordsHolder";
    private HomeData mHomeData;
    private List imgList;
    private int width;
    private int height;
    @BindView(R.id.item_rec_words_banner)
    Banner mItemRecWordsBanner;
    @BindView(R.id.tv_rec_words_banner_center_title)
    TextView mTvRecWordsBannerCenterTitle;
    @BindView(R.id.tv_rec_words_banner_title)
    TextView mTvRecWordsBannerTitle;
    @BindView(R.id.item_rec_words_root)
    ConstraintLayout mItemRecWordsRoot;

    public RecoWordsHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, itemView);
        width = ScreenUtil.getScreenWidth2Dp(App.getContext());
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_hot_imgs_root_height) /
                        App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(HomeData data) {
        mHomeData = data;
        transformData();

    }

    private void transformData() {
        List<RecommendEntity> datas = mHomeData.getDatas();
        if (datas == null || datas.isEmpty()) {
            imgList = new ArrayList();
            imgList.add(R.drawable.recwords_bg);
            imgList.add(R.drawable.recwords_bg);
            imgList.add(R.drawable.recwords_bg);
        } else {
            imgList = new ArrayList();
            for (RecommendEntity bean : datas) {
                mTvRecWordsBannerCenterTitle.setText(bean.getTitle());
                imgList.add(R.drawable.recwords_bg);
            }
        }
        bindData();
    }

    private void bindData() {
        //设置banner样式
        mItemRecWordsBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mItemRecWordsBanner.setImageLoader(new FrescoLoader(width, height));
        //设置图片集合
        mItemRecWordsBanner.setImages(imgList);
        //设置banner动画效果
        mItemRecWordsBanner.setBannerAnimation(Transformer.Default);
//        //设置标题集合（当banner样式有显示title时）
//        mBannerHomeHead.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mItemRecWordsBanner.isAutoPlay(true);
        //设置轮播时间
        mItemRecWordsBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mItemRecWordsBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mItemRecWordsBanner.start();

    }
}
