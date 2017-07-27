package sinolight.cn.qgapp.presenter;

import android.content.Context;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.DBResTypeEntity;
import sinolight.cn.qgapp.data.http.entity.MaterialEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.holder.TreeChildHolder;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IDBResArticleFragmentView;
import sinolight.cn.qgapp.views.view.IDBResMaterialFragmentView;

/**
 * Created by xns on 2017/7/8.
 * 资源库文章标签界面的Presenter
 */

public class DBResArticlePresenter extends BasePresenter<IDBResArticleFragmentView, HttpManager>{
    private static final String TAG = "DBResArticlePresenter";

    private Context mContext;
    private List<KDBResData> mDatas = new ArrayList<>();

    private List<DBResTypeEntity> mTreeTypeList;
    private List<TreeNode> mTreeNodes;
    private List<TreeNode> mRoot;
    private List<TreeNode> mTrees;
    private final TreeNode root = TreeNode.root();

    private HttpSubscriber<List<DBResTypeEntity>> mDBResTypeObserver = new HttpSubscriber<>(
            new OnResultCallBack<List<DBResTypeEntity>>() {

                @Override
                public void onSuccess(List<DBResTypeEntity> dbResTypeEntities) {
                    mTreeTypeList = dbResTypeEntities;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            disposeData();
                        }
                    }).start();
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mDBResTypeObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
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

    /**
     * Return treeMenu root
     * @return
     */
    public TreeNode popTreeMenu() {
        if (mTreeNodes != null && mTreeNodes.size() != 0) {
            root.addChildren(mTreeNodes);
            return root;
        } else {
            showErrorToast(R.string.attention_data_refresh_error);
            return null;
        }
    }

    private void transformKDBResData(int adapterType) {

    }


    private void showSuccess() {

    }

    private void showError() {
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showErrorToast(int msgId) {
        view().showErrorToast(msgId);
    }

    public DBResArticlePresenter(Context context) {
        this.mContext = context;
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

    public void init2Show() {
        if (mTreeNodes == null) {
            // Create treeMenu
            model.getKDBResTypeWithCache(
                    mDBResTypeObserver,
                    AppHelper.getInstance().getCurrentToken(),
                    null,
                    false
            );
        }

    }

}
