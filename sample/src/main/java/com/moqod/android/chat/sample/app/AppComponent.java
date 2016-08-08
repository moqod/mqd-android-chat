package com.moqod.android.chat.sample.app;

import com.moqod.android.chat.di.ChatSingletonComponent;
import com.moqod.android.chat.di.modules.ApplicationModule;
import com.moqod.android.chat.di.modules.DbModule;
import com.moqod.android.chat.di.modules.DomainModule;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 14:11
 */
@Singleton
@Component(modules = {DbModule.class, DomainModule.class, ApplicationModule.class})
public interface AppComponent extends ChatSingletonComponent {
}
