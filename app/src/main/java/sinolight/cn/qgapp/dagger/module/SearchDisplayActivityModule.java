package sinolight.cn.qgapp.dagger.module;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.presenter.SearchDisplayActivityPresenter;
import sinolight.cn.qgapp.views.view.ISearchDisplayActivityView;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的Module
 */
@Module
public class SearchDisplayActivityModule {
    private ISearchDisplayActivityView view;

    public SearchDisplayActivityModule(ISearchDisplayActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    ISearchDisplayActivityView provideISearchActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    SearchDisplayActivityPresenter provideSearchActivityPresenter(Context context, ISearchDisplayActivityView view, DaoSession daoSession) {
        return new SearchDisplayActivityPresenter(context, view, daoSession);
    }

    @Provides
    @PerActivity
    ArrayAdapter<CharSequence> provideArrayAdapter(Activity activity) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                activity,
                R.array.data_base_name,
                R.layout.spinner_layout
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }
}
