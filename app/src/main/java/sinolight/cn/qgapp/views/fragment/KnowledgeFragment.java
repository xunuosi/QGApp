package sinolight.cn.qgapp.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/6/29.
 */

public class KnowledgeFragment extends BaseFragment {

    public static KnowledgeFragment newInstance() {
        return new KnowledgeFragment();
    }

    public KnowledgeFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_knowledge, container, false);
        return fragmentView;
    }
}
