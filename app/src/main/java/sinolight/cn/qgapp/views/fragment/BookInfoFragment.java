package sinolight.cn.qgapp.views.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/7/24.
 * 知识库图书信息界面
 */

public class BookInfoFragment extends ResBaseFragment {
    private static final String KEY = "sinolight.cn.qgapp.views.fragment";
    public static final int TYPE_BOOK_INFO = 0;
    public static final int TYPE_BOOK_INTRODUCTION = 1;
    public static final int TYPE_BOOK_TABLE_OF_CONTENTS = 2;

    private int mType;
    private TextView mTvBookIntroduction;

    public static BookInfoFragment newInstance(int type) {
        BookInfoFragment fragment = new BookInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = 0;
        switch (mType) {
            case TYPE_BOOK_INFO:
                layoutId = R.layout.fragment_book_info;
                break;
            case TYPE_BOOK_INTRODUCTION:
                layoutId = R.layout.fragment_book_introduction;
                break;
            case TYPE_BOOK_TABLE_OF_CONTENTS:
                layoutId = R.layout.fragment_book_info;
                break;
        }
        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (mType) {
            case TYPE_BOOK_INFO:

                break;
            case TYPE_BOOK_INTRODUCTION:
                bindBookIntroduction(view);
                break;
            case TYPE_BOOK_TABLE_OF_CONTENTS:

                break;
        }

    }

    /**
     * 绑定图书简介
     * @param view
     */
    private void bindBookIntroduction(View view) {
        mTvBookIntroduction = view.findViewById(R.id.tv_book_introduction);
        // Fix issue ViewPager and TextView Scroll
        mTvBookIntroduction.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mTvBookIntroduction.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        mTvBookIntroduction.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
