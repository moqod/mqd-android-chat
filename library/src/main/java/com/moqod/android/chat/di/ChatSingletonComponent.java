package com.moqod.android.chat.di;

import android.content.Context;
import com.moqod.android.chat.data.xmpp.XMPPService;
import com.moqod.android.chat.di.modules.ApplicationModule;
import com.moqod.android.chat.di.modules.DbModule;
import com.moqod.android.chat.di.modules.DomainModule;
import com.moqod.android.chat.domain.chats.ChatsRepository;
import com.moqod.android.chat.domain.messages.MessagesRepository;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 07/08/16
 * Time: 23:32
 */
@Singleton
@Component(modules = {DbModule.class, DomainModule.class, ApplicationModule.class})
public interface ChatSingletonComponent {

    void inject(XMPPService xmppService);
    Context context();
    MessagesRepository messagesRepository();
    ChatsRepository chatsRepository();

}
