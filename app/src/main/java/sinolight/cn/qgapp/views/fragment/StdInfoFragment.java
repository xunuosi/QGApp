package sinolight.cn.qgapp.views.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.entity.StdInfoEntity;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * Created by xns on 2017/7/24.
 * 知识库标准信息界面
 */

public class StdInfoFragment extends ResBaseFragment {
    private static final String TYPE_KEY = "sinolight.cn.qgapp.views.fragment.BookInfoFragment_TYPE";
    private static final String DATA_KEY = "sinolight.cn.qgapp.views.fragment.BookInfoFragment_DATA";
    public static final int TYPE_STD_INFO = 0;
    public static final int TYPE_STD_INTRODUCTION = 1;
    public static final int TYPE_STD_TABLE_OF_CONTENTS = 2;

    /**
     * BookInfo View
     */
    private TextView mTvStdFragmentEditionNum;
    private TextView mTvStdFragmentPubTime;
    private TextView mTvStdFragmentExecuteTime;
    private TextView mTvStdFragmentIsn;
    private TextView mTvStdFragmentPubCompanyHolder;
    private TextView mTvStdFragmentPageNum;
    private TextView mTvStdFragmentPageSize;
    private TextView mTvStdFragmentPackage;
    /**
     * BookIntroduction View
     */
    private TextView mTvStdIntroduction;
    // 目录
    private TextView mTvTableOfContent;

    private int mType;
    private StdInfoEntity mStdData;


    public static StdInfoFragment newInstance(int type, StdInfoEntity stdData) {
        StdInfoFragment fragment = new StdInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        bundle.putParcelable(DATA_KEY, stdData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(TYPE_KEY, 0);
        mStdData = getArguments().getParcelable(DATA_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = 0;
        switch (mType) {
            case TYPE_STD_INFO:
                layoutId = R.layout.fragment_std_info;
                break;
            case TYPE_STD_INTRODUCTION:
                layoutId = R.layout.fragment_std_introduction;
                break;
            case TYPE_STD_TABLE_OF_CONTENTS:
                layoutId = R.layout.fragment_table_of_content;
                break;
        }

        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (mType) {
            case TYPE_STD_INFO:
                bindStdInfo(view);
                break;
            case TYPE_STD_INTRODUCTION:
                bindStdIntroduction(view);
                break;
            case TYPE_STD_TABLE_OF_CONTENTS:
                bindStdTableOfContent(view);
                break;
        }

    }

    private void bindStdTableOfContent(View view) {
        mTvTableOfContent = view.findViewById(R.id.tv_table_of_content);
        // Fix issue ViewPager and TextView Scroll
//        mTvTableOfContent.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                mTvTableOfContent.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//        mTvTableOfContent.setMovementMethod(new ScrollingMovementMethod());
        if (Build.VERSION.SDK_INT >= 24) {
            mTvTableOfContent.setText(Html.fromHtml(mStdData.getCatalog(), FROM_HTML_MODE_COMPACT));
        } else {
            mTvTableOfContent.setText(Html.fromHtml(mStdData.getCatalog())); // or for older api
        }

    }

    /**
     * 绑定图书信息
     *
     * @param view
     */
    private void bindStdInfo(View view) {
        mTvStdFragmentEditionNum = view.findViewById(R.id.tv_std_fragment_edition_num);
        mTvStdFragmentPubTime = view.findViewById(R.id.tv_std_fragment_pub_time);
        mTvStdFragmentExecuteTime = view.findViewById(R.id.tv_std_fragment_execute_time);
        mTvStdFragmentIsn = view.findViewById(R.id.tv_std_fragment_isn);
        mTvStdFragmentPubCompanyHolder = view.findViewById(R.id.tv_std_fragment_pub_company_holder);
        mTvStdFragmentPageNum = view.findViewById(R.id.tv_std_fragment_page_num);
        mTvStdFragmentPageSize = view.findViewById(R.id.tv_std_fragment_page_size);
        mTvStdFragmentPackage = view.findViewById(R.id.tv_std_fragment_package);

        mTvStdFragmentEditionNum.setText(formatStr(R.string.text_edition_num_format, getVersionNum()));
        mTvStdFragmentPubTime.setText(formatStr(R.string.text_publish_time_format2, mStdData.getIssuedate()));
        mTvStdFragmentExecuteTime.setText(formatStr(R.string.text_execute_time_format, mStdData.getImdate()));
        mTvStdFragmentIsn.setText(formatStr(R.string.text_isn_format2, mStdData.getStdno()));
        mTvStdFragmentPubCompanyHolder.setText(formatStr(R.string.text_pub_company_format, mStdData.getDept()));
//        mTvStdFragmentPageNum.setText(formatStr(R.string.text_page_num_format, String.valueOf(mBookData.getPage())));
//        mTvStdFragmentPageSize.setText(formatStr(R.string.text_page_size_format, mBookData.getFormat()));
//        mTvStdFragmentPackage.setText(formatStr(R.string.text_package_style_format, mBookData.getZzformat()));
    }

    private String formatStr(int baseStrId, String child) {
        String local = getString(baseStrId);
        return String.format(local, child);
    }

    private String getVersionNum() {
        String str = mStdData.getImdate();
        String[] arr = str.split("-");
        return arr[1];
    }

    /**
     * 绑定图书简介
     *
     * @param view
     */
    private void bindStdIntroduction(View view) {
        mTvStdIntroduction = view.findViewById(R.id.tv_std_introduction);
//        // Fix issue ViewPager and TextView Scroll
//        mTvBookIntroduction.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                mTvBookIntroduction.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//        mTvBookIntroduction.setMovementMethod(new ScrollingMovementMethod());
        mTvStdIntroduction.setText(formatStr(R.string.text_two_empty_format, mStdData.getScope()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
