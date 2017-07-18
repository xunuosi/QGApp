package sinolight.cn.qgapp.views.widget.popmenu;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;


import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;

/**
 * Author：Bro0cL on 2016/12/26.
 */
public class TopRightMenu {
    private static final String TAG = "TopRightMenu";
    private Activity mContext;
    private PopupWindow mPopupWindow;
    private View contentView;
    private View container;

    private static final int DEFAULT_HEIGHT = 480;
    private static final int DEFAULT_WIDTH = 640;
    private int popHeight = DEFAULT_HEIGHT;
    private int popWidth = RecyclerView.LayoutParams.WRAP_CONTENT;
    private boolean showIcon = true;
    private boolean dimBackground = true;
    private boolean needAnimationStyle = true;

    private static final int DEFAULT_ANIM_STYLE = R.style.TRM_ANIM_STYLE;
    private int animationStyle;

    private float alpha = 0.75f;

    public TopRightMenu(Activity context,View contentView) {
        this.mContext = context;
        this.contentView = contentView;
        init();
    }

    private void init() {
        container = LayoutInflater.from(App.getContext()).inflate(R.layout.trm_popup_menu, null, false);
        FrameLayout layout = container.findViewById(R.id.pop_menu_container);
        layout.addView(contentView);
    }

    private PopupWindow getPopupWindow(){
        mPopupWindow = new PopupWindow(App.getContext());
        mPopupWindow.setContentView(container);
        mPopupWindow.setHeight(popHeight);
        mPopupWindow.setWidth(popWidth);
        if (needAnimationStyle){
            mPopupWindow.setAnimationStyle(animationStyle <= 0 ? DEFAULT_ANIM_STYLE : animationStyle);
        }

        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dimBackground) {
                    setBackgroundAlpha(alpha, 1f, 300);
                }
            }
        });

        return mPopupWindow;
    }

    public TopRightMenu setHeight(int height){
        if (height <= 0 && height != RecyclerView.LayoutParams.MATCH_PARENT
                && height != RecyclerView.LayoutParams.WRAP_CONTENT){
            this.popHeight = DEFAULT_HEIGHT;
        }else {
            this.popHeight = height;
        }
        return this;
    }

    public TopRightMenu setWidth(int width){
        if (width <= 0 && width != RecyclerView.LayoutParams.MATCH_PARENT){
            this.popWidth = RecyclerView.LayoutParams.WRAP_CONTENT;
        }else {
            this.popWidth = width;
        }
        return this;
    }

    /**
     * 是否显示菜单图标
     * @param show
     * @return
     */
    public TopRightMenu showIcon(boolean show){
        this.showIcon = show;
        return this;
    }

    /**
     * 是否让背景变暗
     * @param b
     * @return
     */
    public TopRightMenu dimBackground(boolean b){
        this.dimBackground = b;
        return this;
    }

    /**
     * 否是需要动画
     * @param need
     * @return
     */
    public TopRightMenu needAnimationStyle(boolean need){
        this.needAnimationStyle = need;
        return this;
    }

    /**
     * 设置动画
     * @param style
     * @return
     */
    public TopRightMenu setAnimationStyle(int style){
        this.animationStyle = style;
        return this;
    }

    public TopRightMenu showAsDropDown(View anchor){
        showAsDropDown(anchor, 0, 0);
        return this;
    }

    public TopRightMenu showAsDropDown(View anchor, int xoff, int yoff){
        if (mPopupWindow == null){
            getPopupWindow();
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
            if (dimBackground){
                setBackgroundAlpha(1f, alpha, 240);
            }
        }
        return this;
    }

    private void setBackgroundAlpha(float from, float to, int duration) {
        final WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.alpha = (float) animation.getAnimatedValue();
                mContext.getWindow().setAttributes(lp);
            }
        });
        animator.start();
    }

    public void dismiss(){
        if (mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public PopupWindow getPopView() {
        if (mPopupWindow != null) {
            return mPopupWindow;
        }
        return null;
    }
}
