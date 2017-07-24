package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MyTabAdapter;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.BookInfoEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IKDBBookDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity Presenter
 */

public class KDBBookActivityPresenter extends BasePresenter<IKDBBookDetailActivityView, HttpManager> {
    private static final String TAG = "KDBBookActivityPresenter";

    private Context mContext;
    private String resId;
    private BookInfoEntity bookData;

    private HttpSubscriber<BookInfoEntity> mBookObserver = new HttpSubscriber<>(
            new OnResultCallBack<BookInfoEntity>() {

                @Override
                public void onSuccess(BookInfoEntity bookInfoEntity) {
                    if (bookInfoEntity != null) {
                        bookData = bookInfoEntity;
                        showView();
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mDBResTypeObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

    private void showView() {
        view().init2Show(bookData);
    }

    private void showError() {
        view().showRefreshing(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showErrorToast(int msgId) {
        view().showErrorToast(msgId);
    }

    public KDBBookActivityPresenter(Context context, IKDBBookDetailActivityView view) {
        mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {

        unbindView();
    }

    public void init2show(Intent intent) {
        if (intent != null) {
            view().showRefreshing(true);
            resId = intent.getStringExtra(AppContants.Resource.RES_ID);

            // Load data
            model.getKDBBookInfoNoCache(
                    mBookObserver,
                    AppHelper.getInstance().getCurrentToken(),
                    resId
            );
        }
    }
}
