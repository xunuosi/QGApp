package sinolight.cn.qgapp;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import sinolight.cn.qgapp.dagger.component.ApplicationComponent;
import sinolight.cn.qgapp.dagger.component.DaggerApplicationComponent;
import sinolight.cn.qgapp.dagger.module.ApplicationModule;
import sinolight.cn.qgapp.data.db.GreenDaoHelper;
import sinolight.cn.qgapp.data.http.HttpManager;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by xns on 2017/6/29.
 * Application Class
 */

public class App extends Application {

    private static Context instance;
    private ApplicationComponent mApplicationComponent;
    private String userAgent;

    {
        PlatformConfig.setWeixin("wx979afbfaa12ec30d", "45bfeeb7ea9ef55f71884601cb6093e5");
        PlatformConfig.setQQZone("1106566458", "4oLLikvYDHq5S7XF");
        PlatformConfig.
                setSinaWeibo("1922998406", "608371766b36beaf5cf252196f9fe225", "http://www.sinolight.cn");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        instance = this;

        initBugly();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(this, config);
        HttpManager.init(this);
        initDatabase();

        AppHelper.getInstance().init(instance);

        userAgent = AppHelper.getUserAgent(this, "QGApp");

        UMShareAPI.get(this);
        Config.DEBUG = true;
    }

    private void initBugly() {
        // 获取当前包名
        String packageName = instance.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        CrashReport.initCrashReport(getApplicationContext(), "8819aa85fc", false);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private void initDatabase() {
        GreenDaoHelper.initDatabase();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getContext() {
        return instance;
    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(this, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }
}
