package com.moqod.android.chat.sample.chatlist.injection;

import com.moqod.android.chat.sample.app.AppComponent;
import com.moqod.android.chat.sample.chatlist.ChatListFragment;
import injection.ActivityScope;
import com.moqod.android.chat.sample.common.injection.ErrorHandlerModule;
import dagger.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 17:01
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ChatListModule.class, ErrorHandlerModule.class})
public interface ChatListComponent {
    void inject(ChatListFragment fragment);
}
