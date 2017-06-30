package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.VCodeEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.views.view.IRegisterActivityView;

/**
 * Created by xns on 2017/6/29.
 * RegisterActivity Presenter
 */

public class RegisterActivityPresenter extends BasePresenter<IRegisterActivityView, DaoSession> {
    private Context mContext;

    //声明监听
    private HttpSubscriber mVCodeObserver = new HttpSubscriber(new OnResultCallBack<VCodeEntity>() {

        @Override
        public void onSuccess(VCodeEntity vCodeEntity) {
            view().initShow(vCodeEntity.getVcode());
        }

        @Override
        public void onError(int code, String errorMsg) {
            Log.i("xns", "code:" + code + ",errorMsg:" + errorMsg);
        }
    });

    @Inject
    public RegisterActivityPresenter(IRegisterActivityView view, DaoSession daoSession, Context context) {
        mContext = context;
        bindView(view);
        setModel(daoSession);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        unbindView();
        mVCodeObserver.unSubscribe();
    }

    private void getVCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String time = dateFormat.format(new Date());
        // 请求验证码接口
        HttpManager.getInstance().getCode(mVCodeObserver, time);
    }


    public void init2show() {
        getVCode();
    }

    public void refresh2show() {
        getVCode();
    }
}
