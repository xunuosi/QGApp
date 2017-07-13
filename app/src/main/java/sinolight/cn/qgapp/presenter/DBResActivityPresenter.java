package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.views.view.IDBResActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity Presenter
 */

public class DBResActivityPresenter extends BasePresenter<IDBResActivityView,HttpManager> {
    private static final String TAG = "DBResActivityPresenter";
    private Context mContext;
    private String dbType;
    private AppContants.DataBase.Res resType;

    public DBResActivityPresenter(IDBResActivityView view, Context context) {
        this.mContext = context;
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

    public void show(Intent intent) {
        if (intent != null) {
            resType = (AppContants.DataBase.Res) intent.getSerializableExtra(AppContants.DataBase.KEY_RES_TYPE);
            dbType = intent.getStringExtra(AppContants.DataBase.KEY_TYPE);
            showWithResType();
        }
    }

    private void showWithResType() {
        switch (resType) {
            case RES_BOOK:
                view().initShow(mContext.getString(R.string.text_book));
                break;
            case RES_STANDARD:
                view().initShow(mContext.getString(R.string.text_standard));
                break;
            case RES_ARTICLE:
                view().initShow(mContext.getString(R.string.text_article));
                break;
            case RES_IMG:
                view().initShow(mContext.getString(R.string.text_img));
                break;
            case RES_DIC:
                view().initShow(mContext.getString(R.string.text_dictionary));
                break;
            case RES_INDUSTRY:
                view().initShow(mContext.getString(R.string.text_analysis));
                break;
        }
    }
}
