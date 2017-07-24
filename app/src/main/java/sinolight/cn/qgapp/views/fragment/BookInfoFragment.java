package sinolight.cn.qgapp.views.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/7/24.
 * 知识库图书信息界面
 */

public class BookInfoFragment extends ResBaseFragment {

    public static BookInfoFragment newInstance() {
        return new BookInfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_book_info, container, false);

        return fragmentView;
    }
}
