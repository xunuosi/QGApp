package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
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
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.activity.DBResourceActivity;
import sinolight.cn.qgapp.views.widget.FrescoLoader;

/**
 * Created by xns on 2017/7/5.
 * 首页顶部轮播图片
 */

public class HomeHotPicsHolder extends RecyclerView.ViewHolder implements
        ViewPager.OnPageChangeListener,OnBannerListener {
    private static final String TAG = "HomeHotPicsHolder";
    private HomeData mHomeData;
    List<BannerEntity> datas;
    private List imgList;
    private List<String> titles;
    private int width;
    private int height;

    @BindView(R.id.item_hot_imgs_banner)
    Banner mBannerHomeHead;
    @BindView(R.id.item_hot_imgs_root)
    ConstraintLayout mBannerHomeHeadRoot;
    @BindView(R.id.tv_hot_imgs_banner_center_title)
    TextView mTvHotImgsBannerCenterTitle;

    public HomeHotPicsHolder(View layout) {
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
            imgList.add(R.drawable.hotpicture_bg);
            imgList.add(R.drawable.hotpicture_bg);
            imgList.add(R.drawable.hotpicture_bg);

            titles.add(App.getContext().getString(R.string.text_technics_beautiful));
            titles.add(App.getContext().getString(R.string.text_technics_beautiful));
            titles.add(App.getContext().getString(R.string.text_technics_beautiful));
        } else {
            for (BannerEntity bean : datas) {
                imgList.add(bean.getCover());
                titles.add(bean.getTitle());
            }
        }
        bindData();
    }

    private void bindData() {
        //设置banner样式
        mBannerHomeHead.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBannerHomeHead.setImageLoader(new FrescoLoader(width, height, false));
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

        mBannerHomeHead.setOnPageChangeListener(this);
        mBannerHomeHead.setOnBannerListener(this);
        mTvHotImgsBannerCenterTitle.setText(titles.get(0));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 9条数据 position变化范围：1-10，其中到10的时候直接又变成1需要特殊处理
        if (position >= titles.size() + 1) {
            return;
        }

        mTvHotImgsBannerCenterTitle.setText(titles.get(position - 1));
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void OnBannerClick(int position) {
        gotoImgSetInfo(position);
    }

    private void gotoImgSetInfo(int position) {
        Intent callIntent = DBResourceActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.DataBase.KEY_ID, datas.get(position).getId());
        callIntent.putExtra(AppContants.DataBase.KEY_RES_TYPE, AppContants.DataBase.Res.RES_IMG);
        callIntent.putExtra(AppContants.DataBase.KEY_TYPE, "");
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }
}
