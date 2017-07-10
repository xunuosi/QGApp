package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.adapter.KnowledgeAdapter;

/**
 * Created by xns on 2017/7/5.
 * KnowledgeFragment View层接口
 */

public interface IKnowledgeFragmentView {

    void showLoading(boolean enable);

    void showView(KnowledgeAdapter knowledgeAdapter);
}
