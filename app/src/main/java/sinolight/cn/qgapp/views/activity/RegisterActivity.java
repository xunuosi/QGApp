package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.SimpleDateFormat;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.VCodeEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;

/**
 * Created by xns on 2017/6/30.
 * 注册界面
 */

public class RegisterActivity extends BaseActivity {
    //声明监听
    private HttpSubscriber mHttpObserver = new HttpSubscriber(new OnResultCallBack<VCodeEntity>() {

        @Override
        public void onSuccess(VCodeEntity vCodeEntity) {
            Log.i("xns", "code:" + vCodeEntity.toString());
        }

        @Override
        public void onError(int code, String errorMsg) {
            Log.i("xns", "code:" + code + ",errorMsg:" + errorMsg);
        }
    });


    public static Intent getCallIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

        String time = "20170630135015";
        //发起请求
        HttpManager.getInstance().getCode(mHttpObserver, time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHttpObserver.unSubscribe();
    }
}
