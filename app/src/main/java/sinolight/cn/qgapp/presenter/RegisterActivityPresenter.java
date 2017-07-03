package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.Account;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.TokenEntity;
import sinolight.cn.qgapp.data.http.entity.VCodeEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.utils.TextFormatUtil;
import sinolight.cn.qgapp.views.activity.LoginActivity;
import sinolight.cn.qgapp.views.view.IRegisterActivityView;

/**
 * Created by xns on 2017/6/29.
 * RegisterActivity Presenter
 */

public class RegisterActivityPresenter extends BasePresenter<IRegisterActivityView, DaoSession> {
    private static final String TAG = "RegisterActivityPresent";
    private Context mContext;
    private Account user;
    private String vCode;

    //声明监听
    private HttpSubscriber mVCodeObserver = new HttpSubscriber(new OnResultCallBack<VCodeEntity>() {

        @Override
        public void onSuccess(VCodeEntity vCodeEntity) {
            vCode = vCodeEntity.getVcode();
            L.d(TAG, "vCode:" + vCode);
            view().initShow(vCode);
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "code:" + code + ",errorMsg:" + errorMsg);
            view().showToastMsg(R.string.error_internet);
            view().initShow(null);
        }
    });

    private HttpSubscriber mRegisterObserver = new HttpSubscriber(new OnResultCallBack<TokenEntity>() {

        @Override
        public void onSuccess(TokenEntity tokenEntity) {
            // 用户存储到数据库中
            model.getAccountDao().save(user);
            view().showToastMsg(R.string.text_register_success);
            Intent intent = LoginActivity.getCallIntent(mContext);
            intent.putExtra(AppContants.Account.USER_NAME, user.getName());
            view().gotoActivity(intent);
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "code:" + code + ",errorMsg:" + errorMsg);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
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
        mRegisterObserver.unSubscribe();
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


    public void registerAccount(String account, String email, String pwd, String rePwd, String vCode) {
        if (checkoutData(account, email, pwd, rePwd, vCode)) {
            // 请求注册接口
            HttpManager.getInstance().register(mRegisterObserver, account, email, pwd, rePwd, vCode);

            user = new Account();
            user.setName(account);
            user.setPassword(pwd);
            user.setEmail(email);
        }

    }

    private boolean checkoutData(String account, String email, String pwd, String rePwd, String vCode) {
        if (TextUtils.isEmpty(account)) {
            view().showToastMsg(R.string.text_user_empty);
            return false;
        } else if (!TextFormatUtil.isUserName(account) && !TextFormatUtil.isEmail(account) && !TextFormatUtil.isMobile(account)) {
            view().showToastMsg(R.string.text_valid_user);
            return false;
        } else if (TextUtils.isEmpty(email)) {
            view().showToastMsg(R.string.text_email_empty);
            return false;
        } else if (!TextFormatUtil.isEmail(email)) {
            view().showToastMsg(R.string.text_valid_email);
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            view().showToastMsg(R.string.text_pwd_empty);
            return false;
        } else if (!TextFormatUtil.isPassword(pwd)) {
            view().showToastMsg(R.string.text_valid_pwd);
            return false;
        } else if (TextUtils.isEmpty(rePwd)) {
            view().showToastMsg(R.string.text_repwd_empty);
            return false;
        } else if (!TextFormatUtil.isPassword(rePwd)) {
            view().showToastMsg(R.string.text_valid_pwd);
            return false;
        } else if (!pwd.equals(rePwd)) {
            view().showToastMsg(R.string.text_pwd_not_equal);
            return false;
        } else if (TextUtils.isEmpty(vCode)) {
            view().showToastMsg(R.string.text_vcode_empty);
            return false;
        } else if (!this.vCode.equalsIgnoreCase(vCode)) {
            view().showToastMsg(R.string.text_vcode_not_equal);
            return false;
        }

        return true;
    }
}
