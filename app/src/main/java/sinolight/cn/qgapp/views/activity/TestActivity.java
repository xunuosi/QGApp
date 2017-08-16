package sinolight.cn.qgapp.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.entity.DBResTypeEntity;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;

/**
 * Created by admin on 2017/7/16.
 */

public class TestActivity extends AppCompatActivity {
    private String json = "[\n" +
            "        {\n" +
            "            \"id\": \"12.\",\n" +
            "            \"name\": \"衡器分类\",\n" +
            "            \"pid\": \"0\",\n" +
            "            \"open\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.\",\n" +
            "            \"name\": \"结构原理\",\n" +
            "            \"pid\": \"12.\",\n" +
            "            \"open\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.1.\",\n" +
            "            \"name\": \"机械衡器\",\n" +
            "            \"pid\": \"12.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.1.1.\",\n" +
            "            \"name\": \"弹簧度盘秤\",\n" +
            "            \"pid\": \"12.1.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.1.2.\",\n" +
            "            \"name\": \"杠杆式天平\",\n" +
            "            \"pid\": \"12.1.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.1.3.\",\n" +
            "            \"name\": \"台秤\",\n" +
            "            \"pid\": \"12.1.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.1.4.\",\n" +
            "            \"name\": \"案秤\",\n" +
            "            \"pid\": \"12.1.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.1.5.\",\n" +
            "            \"name\": \"地中衡\",\n" +
            "            \"pid\": \"12.1.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.1.6.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.1.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.2.\",\n" +
            "            \"name\": \"电子衡器\",\n" +
            "            \"pid\": \"12.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.2.1.\",\n" +
            "            \"name\": \"电子汽车衡\",\n" +
            "            \"pid\": \"12.1.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.2.2.\",\n" +
            "            \"name\": \"电子吊秤\",\n" +
            "            \"pid\": \"12.1.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.2.3.\",\n" +
            "            \"name\": \"电子叉车秤\",\n" +
            "            \"pid\": \"12.1.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.2.4.\",\n" +
            "            \"name\": \"电子台秤\",\n" +
            "            \"pid\": \"12.1.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.1.2.5.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.1.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.\",\n" +
            "            \"name\": \"称重状态\",\n" +
            "            \"pid\": \"12.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.1.\",\n" +
            "            \"name\": \"静态衡器\",\n" +
            "            \"pid\": \"12.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.1.1.\",\n" +
            "            \"name\": \"天平\",\n" +
            "            \"pid\": \"12.2.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.1.2.\",\n" +
            "            \"name\": \"台秤\",\n" +
            "            \"pid\": \"12.2.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.1.3.\",\n" +
            "            \"name\": \"案秤\",\n" +
            "            \"pid\": \"12.2.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.1.4.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.2.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.2.\",\n" +
            "            \"name\": \"动态衡器\",\n" +
            "            \"pid\": \"12.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.2.1.\",\n" +
            "            \"name\": \"皮带秤\",\n" +
            "            \"pid\": \"12.2.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.2.2.\",\n" +
            "            \"name\": \"分捡秤\",\n" +
            "            \"pid\": \"12.2.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.2.3.\",\n" +
            "            \"name\": \"自动轨道衡\",\n" +
            "            \"pid\": \"12.2.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.2.4.\",\n" +
            "            \"name\": \"骑车衡\",\n" +
            "            \"pid\": \"12.2.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.2.2.5.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.2.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.\",\n" +
            "            \"name\": \"用途\",\n" +
            "            \"pid\": \"12.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.1.\",\n" +
            "            \"name\": \"商业用衡器\",\n" +
            "            \"pid\": \"12.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.1.1.\",\n" +
            "            \"name\": \"计价秤\",\n" +
            "            \"pid\": \"12.3.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.1.2.\",\n" +
            "            \"name\": \"邮资秤\",\n" +
            "            \"pid\": \"12.3.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.1.3.\",\n" +
            "            \"name\": \"标签零售秤\",\n" +
            "            \"pid\": \"12.3.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.1.4.\",\n" +
            "            \"name\": \"商用案秤\",\n" +
            "            \"pid\": \"12.3.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.1.5.\",\n" +
            "            \"name\": \"商用台秤\",\n" +
            "            \"pid\": \"12.3.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.1.6.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.3.1.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.\",\n" +
            "            \"name\": \"工业用衡器\",\n" +
            "            \"pid\": \"12.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.1.\",\n" +
            "            \"name\": \"吊秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.2.\",\n" +
            "            \"name\": \"汽车衡\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.3.\",\n" +
            "            \"name\": \"轨道衡\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.4.\",\n" +
            "            \"name\": \"配料秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.5.\",\n" +
            "            \"name\": \"定量包装秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.6.\",\n" +
            "            \"name\": \"液体灌装秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.7.\",\n" +
            "            \"name\": \"钢包秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.8.\",\n" +
            "            \"name\": \"钢材秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.9.\",\n" +
            "            \"name\": \"分捡秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.10.\",\n" +
            "            \"name\": \"皮带秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.11.\",\n" +
            "            \"name\": \"转子秤\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.2.12.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.3.2.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.3.\",\n" +
            "            \"name\": \"医用衡器\",\n" +
            "            \"pid\": \"12.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.3.1.\",\n" +
            "            \"name\": \"婴儿秤\",\n" +
            "            \"pid\": \"12.3.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.3.2.\",\n" +
            "            \"name\": \"病床秤\",\n" +
            "            \"pid\": \"12.3.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.3.3.\",\n" +
            "            \"name\": \"透析用秤\",\n" +
            "            \"pid\": \"12.3.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.3.4.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.3.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.4.\",\n" +
            "            \"name\": \"实验室用衡器\",\n" +
            "            \"pid\": \"12.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.4.1.\",\n" +
            "            \"name\": \"精密分析天平\",\n" +
            "            \"pid\": \"12.3.4.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.4.2.\",\n" +
            "            \"name\": \"实验室天平\",\n" +
            "            \"pid\": \"12.3.4.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.4.3.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.3.4.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.5.\",\n" +
            "            \"name\": \"家庭用衡器\",\n" +
            "            \"pid\": \"12.3.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.5.1.\",\n" +
            "            \"name\": \"厨房用秤\",\n" +
            "            \"pid\": \"12.3.5.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.5.2.\",\n" +
            "            \"name\": \"体重秤\",\n" +
            "            \"pid\": \"12.3.5.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.5.3.\",\n" +
            "            \"name\": \"“手掌”秤\",\n" +
            "            \"pid\": \"12.3.5.\",\n" +
            "            \"open\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"12.3.5.4.\",\n" +
            "            \"name\": \"其它\",\n" +
            "            \"pid\": \"12.3.5.\",\n" +
            "            \"open\": false\n" +
            "        }\n" +
            "    ]";

    private List<DBResTypeEntity> mDatas = new ArrayList<>();
    private List<TreeNode> mTrees = new ArrayList<>();
    private List<TreeNode> mRoot;
    private FrameLayout contentLayot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        contentLayot = (FrameLayout) findViewById(R.id.test_content_root);
        toJson();
    }

    private void toJson() {
        Gson gson = new Gson();
        mDatas = gson.fromJson(json, new TypeToken<List<DBResTypeEntity>>() {
        }.getType());
        changeTreeNode();
    }

    private void changeTreeNode() {
        for (DBResTypeEntity bean : mDatas) {
            TreeNode p = new TreeNode(new TreeParentHolder.IconTreeItem(
                    bean.getId(),
                    bean.getPid(),
                    bean.getName(),
                    bean.isHaschild())).setViewHolder(new TreeParentHolder(TestActivity.this));
            mTrees.add(p);
        }

        mRoot = createTree("0", mTrees);
        TreeNode treeNode = mRoot.get(0);
        List<TreeNode> mTreeNodes = treeNode.getChildren();
        TreeNode root = TreeNode.root();
        root.addChildren(mTreeNodes);
//
//        TreeNode root = TreeNode.root();
//        TreeNode a = mTrees.get(0);
//        TreeNode a1 = mTrees.get(1);
//        TreeNode a2 = mTrees.get(2);
//
//        a.addChildren(a1, a2);
//        root.addChild(a);
//
        AndroidTreeView tView = new AndroidTreeView(TestActivity.this, root);
        tView.setDefaultAnimation(true);
//        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom, true);
        tView.setDefaultViewHolder(TreeParentHolder.class);
//        tView.setDefaultNodeClickListener(DBResourceActivity.this);
        tView.setUseAutoToggle(true);

        contentLayot.addView(tView.getView());
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
}
