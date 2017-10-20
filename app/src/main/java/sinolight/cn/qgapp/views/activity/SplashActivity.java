package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.ScreenUtil;

/**
 * Created by xns on 2017/7/4.
 * Splash Activity
 */

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private static final int sleepTime = 2000;
    @Inject
    Context mContext;
    @BindView(R.id.iv_splash)
    SimpleDraweeView mIvSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        int width = ScreenUtil.getScreenWidth2Dp(mContext);
        int height = ScreenUtil.getScreenHeight2Dp(mContext);
        ImageUtil.frescoShowImageByResId(
                mContext,
                R.drawable.splash_icon,
                mIvSplash,
                width,
                height
        );
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (AppHelper.getInstance().isLogined()) {
                    long start = System.currentTimeMillis();
                    AppHelper.getInstance().getCurrentUserName();
                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //enter main screen
                    Intent callIntent = LoginActivity.getCallIntent(mContext);
                    callIntent.putExtra(AppContants.Account.IS_LOGINED, true);
                    startActivity(callIntent);
                    finish();
                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(LoginActivity.getCallIntent(mContext));
                    finish();
                }
            }
        }).start();
    }
}
