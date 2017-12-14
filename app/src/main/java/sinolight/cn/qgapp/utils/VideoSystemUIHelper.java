package sinolight.cn.qgapp.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

/**
 * 针对视频播放页SystemUI处理
 * Created by xns on 2017/12/14.
 */

public class VideoSystemUIHelper {
    /**
     * 竖屏状态栏（显示）导航栏（不处理）；
     */
    public static final int P_HIDE_STATUS = 0x0001;
    /**
     * 竖屏状态栏（隐藏）导航栏（不处理）；
     */
    public static final int P_SHOW_STATUS = 0x0002;
    /**
     * 横屏状态栏（隐藏）导航栏（隐藏）。
     */
    public static final int L_HIDE_STATUS = 0x0003;
    /**
     * 横屏状态栏（隐藏）导航栏（显示）；
     */
    public static final int L_SHOW_STATUS = 0x0004;
    public static final int DEFAULT_P_STATUS = P_HIDE_STATUS;
    public static final int DEFAULT_L_STATUS = L_HIDE_STATUS;
    private int mSystemUIStatus = P_SHOW_STATUS;
    private View mRoot;
    private IOnSystemUIChangeListener mListener;

    public VideoSystemUIHelper(View root, @NonNull IOnSystemUIChangeListener listener){
        mRoot = root;
        mListener = listener;
    }
    /**
     * 处理横竖屏的systgemUi变化
     *
     * @param status
     */
    @TargetApi(16)
    public void handleSystemUI(int status) {
        mSystemUIStatus = status;
        int systemUiVisibility = 0;
        switch (status) {
            case P_SHOW_STATUS: {
                systemUiVisibility =
                        SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE;
                mListener.onSystemUIChange(P_SHOW_STATUS);
                break;
            }
            case P_HIDE_STATUS: {
                systemUiVisibility =
                        SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
                mListener.onSystemUIChange(P_HIDE_STATUS);
                break;
            }
            case L_SHOW_STATUS: {
                systemUiVisibility =  SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                mListener.onSystemUIChange(L_SHOW_STATUS);
                break;
            }
            case L_HIDE_STATUS: {
                systemUiVisibility =
                        SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                mListener.onSystemUIChange(L_HIDE_STATUS);
                break;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mRoot.setSystemUiVisibility(systemUiVisibility);
        }
    }
    public interface  IOnSystemUIChangeListener {
        void onSystemUIChange(int status);
    }
}
