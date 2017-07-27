package sinolight.cn.qgapp.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import javax.inject.Inject;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.presenter.DBResArticlePresenter;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IDBResArticleFragmentView;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

/**
 * Created by xns on 2017/7/26.
 * 热门素材Fragment
 */

public class DBResArticleFragment extends BaseFragment implements IDBResArticleFragmentView,TreeNode.TreeNodeClickListener,PopupWindow.OnDismissListener {
    @Inject
    DBResArticlePresenter mPresenter;
    // TreeMenu
    private TopRightMenu mTopRightMenu;
    private AndroidTreeView tView;

    public static DBResArticleFragment newInstance() {
        return new DBResArticleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_res_db_articlel, container, false);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
        mPresenter.bindView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.init2Show();
    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void showErrorToast(int msgId) {

    }

    @Override
    public void init2Show(CommonTitleAdapter adapter) {

    }

    @Override
    public void gotoActivity(Intent callIntent) {

    }

    public void popTreeMenu(View target) {
        TreeNode treeRoot = mPresenter.popTreeMenu();
        if (treeRoot != null) {
            if (tView == null) {
                tView = new AndroidTreeView(getActivity(), treeRoot);
//                tView.setDefaultAnimation(true);
//                tView.setUse2dScroll(true);
                tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
                tView.setDefaultViewHolder(TreeParentHolder.class);
                tView.setDefaultNodeClickListener(this);
                tView.setUseAutoToggle(true);
            }
            if (mTopRightMenu == null) {
                mTopRightMenu = new TopRightMenu(getActivity(), tView.getView());
                mTopRightMenu
                        .setHeight(850)     //默认高度480
                        .setWidth(450)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .showAsDropDown(target, -225, 0);
            } else {
                mTopRightMenu.showAsDropDown(target, -225, 0);
            }
            if (mTopRightMenu.getPopView() != null) {
                mTopRightMenu.getPopView().setOnDismissListener(this);
            }
        } else {
            showToastMessage(getString(R.string.text_tree_menu_loading));
        }
    }

    @Override
    public void onDismiss() {
        // recover window Alpha
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 1.0f;
        getActivity().getWindow().setAttributes(lp);
        // Request themeType Data
    }

    @Override
    public void onClick(TreeNode node, Object value) {

    }
}
