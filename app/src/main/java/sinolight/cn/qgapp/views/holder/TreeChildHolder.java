package sinolight.cn.qgapp.views.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

import sinolight.cn.qgapp.R;

/**
 * Created by Bogdan Melnychuk on 2/15/15, modified by Szigeti Peter 2/2/16.
 * Icon + TextView 最简树View
 */
public class TreeChildHolder extends TreeNode.BaseNodeViewHolder<TreeParentHolder.IconTreeItem> {
    private TextView tvValue;
    private ImageView arrowView;

    public TreeChildHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, TreeParentHolder.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_tree_child, null, false);

        tvValue = (TextView) view.findViewById(R.id.tree_child_node_value);
        tvValue.setText(value.name);


        arrowView = (ImageView) view.findViewById(R.id.tree_child_arrow_icon);
        arrowView.setPadding(20,10,10,10);
        if (node.isLeaf()) {
            arrowView.setVisibility(View.GONE);
        }
        arrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tView.toggleNode(node);
            }
        });

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setImageResource(
                active ? R.drawable.ic_keyboard_arrow_down_black_24dp : R.drawable.ic_keyboard_arrow_right_black_24dp);
    }
}
