package sinolight.cn.qgapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.google.android.exoplayer2.ExoPlayerLibraryInfo;

import sinolight.cn.qgapp.utils.L;

/**
 * Created by xns on 2017/7/4.
 * App的辅助类
 */

public class AppHelper {
    private static final String TAG = "AppHelper";
    private static AppHelper instance = null;
    private AppModel mModel;
    private String userName;
    private String pwd;
    private String token;

    private AppHelper() {
    }

    public synchronized static AppHelper getInstance() {
        if (instance == null) {
            instance = new AppHelper();
        }
        return instance;
    }

    public void init(Context context) {
        mModel = new AppModel(context);
    }

    public String getCurrentUserName() {
        if (userName == null) {
            userName = mModel.getCurrentUserName();
        }
        return userName;
    }

    public void setCurrentUserName(String userName) {
        this.userName = userName;
        mModel.setCurrentUserName(userName);
    }

    public String getCurrentToken() {
        if (token == null) {
            token = mModel.getCurrentToken();
        }
        return token;
    }

    public void setCurrentToken(String token) {
        this.token = token;
        mModel.setCurrentToken(token);
    }

    public boolean isLogined() {
        return this.getCurrentToken() != null && this.getCurrentUserName() != null && this.getCurrentPW() != null;
    }

    public String getCurrentPW() {
        if (pwd == null) {
            pwd = mModel.getCurrentPW();
        }
        return pwd;
    }

    public void setCurrentPW(String pwd) {
        this.pwd = pwd;
        mModel.setCurrentPW(pwd);
    }

    /**
     * Returns a user agent string based on the given application name and the library version.
     *
     * @param context A valid context of the calling application.
     * @param applicationName String that will be prefix'ed to the generated user agent.
     * @return A user agent string generated using the applicationName and the library version.
     */
    public static String getUserAgent(Context context, String applicationName) {
        String versionName;
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "?";
        }
        return applicationName + "/" + versionName + " (Linux;Android " + Build.VERSION.RELEASE
                + ") " + ExoPlayerLibraryInfo.VERSION_SLASHY;
    }

}

