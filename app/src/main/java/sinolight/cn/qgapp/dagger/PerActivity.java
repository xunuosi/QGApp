package sinolight.cn.qgapp.dagger;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xns on 2017/6/1.
 * Activity生命周期相同的注入组件
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
