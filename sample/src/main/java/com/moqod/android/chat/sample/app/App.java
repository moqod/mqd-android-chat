package com.moqod.android.chat.sample.app;

import android.app.Application;
import android.content.Context;
import com.moqod.android.chat.di.modules.ApplicationModule;
import com.moqod.android.chat.sample.common.HasComponent;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 14:10
 */
public class App extends Application implements HasComponent<AppComponent> {

    public static App get(Context context) {
        return ((App) context.getApplicationContext());
    }

    private AppComponent mComponent;

    @Override
    public AppComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerAppComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mComponent;
    }
}
