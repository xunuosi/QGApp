package sinolight.cn.qgapp.presenter;

import android.content.Context;

import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.views.view.IKnowledgeFragmentView;

/**
 * Created by xns on 2017/7/8.
 * 知识库界面的Presenter
 */

public class KnowledgePresenter extends BasePresenter<IKnowledgeFragmentView, HttpManager>{
    private static final String TAG = "KnowledgePresenter";
    private Context mContext;

    public KnowledgePresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        unbindView();
    }
}
