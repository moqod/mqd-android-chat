package com.moqod.android.chat.sample.chatroom.di;

import com.moqod.android.chat.sample.app.AppComponent;
import com.moqod.android.chat.sample.chatroom.ChatRoomFragment;
import com.moqod.android.chat.sample.common.ActivityScope;
import dagger.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 12:06
 */
@ActivityScope
@Component(modules = {ChatRoomModule.class}, dependencies = AppComponent.class)
public interface ChatRoomComponent {

    void inject(ChatRoomFragment fragment);

}
