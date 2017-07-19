package sinolight.cn.qgapp.views.view;

import com.unnamed.b.atv.model.TreeNode;

import sinolight.cn.qgapp.adapter.KDBResAdapter;

/**
 * Created by xns on 2017/6/29.
 * 行业库资源界面
 */

public interface IDBResActivityView {

    void initShow(String title);

    void popTreeMenu(TreeNode root);

    void showToast(int msgId);

    void showListView(KDBResAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

    void showTab(boolean enable);

    void showFooterView(boolean enable, String msg);
}
