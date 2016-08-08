package com.moqod.android.chat.sample.app;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.moqod.android.chat.Logging;
import com.moqod.android.chat.di.modules.ApplicationModule;
import injection.HasComponent;

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
    public void onCreate() {
        super.onCreate();
        Logging.ENABLED = true;
        Stetho.initializeWithDefaults(this);
    }

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
