package com.moqod.android.chat.di.modules;

import com.moqod.android.chat.data.chats.LocalChatsRepository;
import com.moqod.android.chat.data.messages.LocalMessagesRepository;
import com.moqod.android.chat.domain.chats.ChatsRepository;
import com.moqod.android.chat.domain.messages.MessagesRepository;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 07/08/16
 * Time: 23:39
 */
@Module
public class DomainModule {

    @Provides
    @Singleton
    public MessagesRepository provideMessagesRepository(LocalMessagesRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    public ChatsRepository provideChatsRepository(LocalChatsRepository repository) {
        return repository;
    }

}
