package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.data.http.entity.RecommendEntity;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.widget.FrescoLoader;

/**
 * Created by xns on 2017/7/5.
 * 热门词条
 */

public class RecoWordsHolder extends RecyclerView.ViewHolder implements
        ViewPager.OnPageChangeListener,OnBannerListener {
    private static final String TAG = "RecoWordsHolder";
    private HomeData mHomeData;
    private List<RecommendEntity> datas;
    private List imgList;
    private int width;
    private int height;
    private List<String> titles;
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
        datas = mHomeData.getDatas();
        imgList = new ArrayList();
        titles = new ArrayList<>();
        if (datas == null || datas.isEmpty()) {
            imgList.add(R.drawable.recwords_bg);
            imgList.add(R.drawable.recwords_bg);
            imgList.add(R.drawable.recwords_bg);
            titles.add(mTvRecWordsBannerCenterTitle.getText().toString());
            titles.add(mTvRecWordsBannerCenterTitle.getText().toString());
            titles.add(mTvRecWordsBannerCenterTitle.getText().toString());
        } else {
            for (RecommendEntity bean : datas) {
                titles.add(bean.getTitle());
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
//        mItemRecWordsBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mItemRecWordsBanner.isAutoPlay(true);
        //设置轮播时间
        mItemRecWordsBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mItemRecWordsBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mItemRecWordsBanner.start();

        mItemRecWordsBanner.setOnPageChangeListener(this);
        mItemRecWordsBanner.setOnBannerListener(this);

        mTvRecWordsBannerCenterTitle.setText(titles.get(0));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        L.d(TAG, "position:" + position);
        // 9条数据 position变化范围：1-10，其中到10的时候直接又变成1需要特殊处理
        if (position == titles.size() + 1) {
            return;
        }
        mTvRecWordsBannerCenterTitle.setText(titles.get(position - 1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void OnBannerClick(int position) {
        L.d(TAG, "title:" + datas.get(position).getTitle());
    }
}
