package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResTypeEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.holder.TreeChildHolder;
import sinolight.cn.qgapp.views.view.IDBResActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity Presenter
 */

public class DBResActivityPresenter extends BasePresenter<IDBResActivityView, HttpManager> {
    private static final String TAG = "DBResActivityPresenter";
    private Context mContext;
    private String dbType;
    private AppContants.DataBase.Res resType;
    private List<DBResTypeEntity> mTreeTypeList;
    private List<TreeNode> mTreeNodes;
    private List<TreeNode> mRoot;
    private List<TreeNode> mTrees;
    private final TreeNode root = TreeNode.root();

    private HttpSubscriber<List<DBResTypeEntity>> mDBResTypeObserver = new HttpSubscriber<>(new OnResultCallBack<List<DBResTypeEntity>>() {

        @Override
        public void onSuccess(List<DBResTypeEntity> dbResTypeEntities) {
            mTreeTypeList = dbResTypeEntities;
            disposeData();

        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "hotPicsObserver code:" + code + ",errorMsg:" + errorMsg);
        }
    });

    /**
     * 处理数据的方法用于TreeMenu的建立
     */
    private void disposeData() {
        mTrees = new ArrayList<>();
        for (DBResTypeEntity bean : mTreeTypeList) {
            if (bean.getPid().equals(AppContants.DataBase.TREE_PID)) {
                TreeNode p = new TreeNode(new TreeParentHolder.IconTreeItem(
                        bean.getId(),
                        bean.getPid(),
                        bean.getName(),
                        bean.isHaschild())).setViewHolder(new TreeParentHolder(mContext));
                mTrees.add(p);
            } else {
                TreeNode c = new TreeNode(new TreeParentHolder.IconTreeItem(
                        bean.getId(),
                        bean.getPid(),
                        bean.getName(),
                        bean.isHaschild())).setViewHolder(new TreeChildHolder(mContext));
                mTrees.add(c);
            }
        }

        mRoot = createTree(AppContants.DataBase.TREE_PID, mTrees);
        TreeNode treeNode = mRoot.get(0);
        mTreeNodes = treeNode.getChildren();
    }

    public List<TreeNode> createTree(String pid, List<TreeNode> treeRes) {
        List<TreeNode> temp = new ArrayList<>();
        for (TreeNode treeRe : treeRes) {
            if (pid.equals(getPid(treeRe))) {
                List<TreeNode> child = this.createTree(getId(treeRe), treeRes);
                treeRe.addChildren(child);
                temp.add(treeRe);
            }
        }
        return temp;
    }

    private String getPid(TreeNode node) {
        TreeParentHolder.IconTreeItem value = (TreeParentHolder.IconTreeItem) node.getValue();
        return value.pid;
    }

    private String getId(TreeNode node) {
        TreeParentHolder.IconTreeItem value = (TreeParentHolder.IconTreeItem) node.getValue();
        return value.id;
    }

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
        mDBResTypeObserver.unSubscribe();
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
        // 请求分类数据
        model.getDBResTypeWithCache(
                mDBResTypeObserver,
                AppHelper.getInstance().getCurrentToken(),
                dbType,
                false);
    }

    public void popTreeMenu() {
        root.addChildren(mTreeNodes);
        view().popTreeMenu(root);
    }



}
