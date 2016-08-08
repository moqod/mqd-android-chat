package com.moqod.android.chat.sample.mainscreen.di;

import com.moqod.android.chat.sample.addchat.injection.AddChatModule;
import com.moqod.android.chat.sample.addchat.injection.AddChatStubComponent;
import com.moqod.android.chat.sample.app.AppComponent;
import com.moqod.android.chat.sample.chatlist.injection.ChatListComponent;
import com.moqod.android.chat.sample.chatlist.injection.ChatListModule;
import com.moqod.android.chat.sample.chatroom.di.ChatRoomComponent;
import com.moqod.android.chat.sample.chatroom.di.ChatRoomModule;
import injection.ActivityScope;
import com.moqod.android.chat.sample.common.injection.ErrorHandlerModule;
import dagger.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 17:05
 */
@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = {ChatListModule.class, AddChatModule.class, ChatRoomModule.class, ErrorHandlerModule.class})
public interface MainActivityComponent extends ChatListComponent, AddChatStubComponent, ChatRoomComponent {
}
