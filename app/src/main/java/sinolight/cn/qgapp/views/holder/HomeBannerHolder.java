package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.widget.FrescoLoader;

/**
 * Created by xns on 2017/7/5.
 * 首页顶部轮播图片
 */

public class HomeBannerHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "HomeBannerHolder";
    private HomeData mHomeData;
    private List imgList;
    private int width;
    private int height;

    @BindView(R.id.banner_home_head)
    Banner mBannerHomeHead;
    @BindView(R.id.banner_home_head_root)
    ConstraintLayout mBannerHomeHeadRoot;

    public HomeBannerHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, itemView);
        width = ScreenUtil.getScreenWidth2Dp(App.getContext());
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_head_banner_root_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(HomeData data) {
        mHomeData = data;
        transformData();
    }

    private void transformData() {
        List<BannerEntity> datas = mHomeData.getDatas();
        if (datas == null || datas.isEmpty()) {
            imgList = new ArrayList();
            imgList.add(R.drawable.home_banner);
            imgList.add(R.drawable.home_banner);
            imgList.add(R.drawable.home_banner);
        } else {
            imgList = new ArrayList();
            for (BannerEntity bean : datas) {
                imgList.add(bean.getCover());
            }
        }
        bindData();
    }

    private void bindData() {
        //设置banner样式
        mBannerHomeHead.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBannerHomeHead.setImageLoader(new FrescoLoader(width, height, true));
        //设置图片集合
        mBannerHomeHead.setImages(imgList);
        //设置banner动画效果
        mBannerHomeHead.setBannerAnimation(Transformer.Default);
//        //设置标题集合（当banner样式有显示title时）
//        mBannerHomeHead.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBannerHomeHead.isAutoPlay(true);
        //设置轮播时间
        mBannerHomeHead.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBannerHomeHead.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBannerHomeHead.start();
    }
}
