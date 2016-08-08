package com.moqod.android.chat.sample.chatroom.di;

import com.moqod.android.chat.sample.app.AppComponent;
import com.moqod.android.chat.sample.chatroom.ChatRoomFragment;
import injection.ActivityScope;
import com.moqod.android.chat.sample.common.injection.ErrorHandlerModule;
import dagger.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 12:06
 */
@ActivityScope
@Component(modules = {ChatRoomModule.class, ErrorHandlerModule.class}, dependencies = AppComponent.class)
public interface ChatRoomComponent {

    void inject(ChatRoomFragment fragment);

}
