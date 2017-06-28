package sinolight.cn.qgapp.views.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import sinolight.cn.qgapp.R;

public class HomeActivity extends BaseActivity {

    private NavigationController mNavigationController;
    @BindView(R.id.bottom_tab)
    PageBottomTabLayout bottomTab;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        mNavigationController = bottomTab.material()
                .addItem(R.drawable.bg_tab_home, getString(R.string.bottomBar_home))
                .addItem(R.drawable.bg_tab_knowledge, getString(R.string.bottomBar_knowledge))
                .addItem(R.drawable.bg_tab_resource, getString(R.string.bottomBar_res))
                .addItem(R.drawable.bg_tab_user, getString(R.string.bottomBar_user))
                .build();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.im_back_arrow)
    public void onViewClicked() {
    }
}
