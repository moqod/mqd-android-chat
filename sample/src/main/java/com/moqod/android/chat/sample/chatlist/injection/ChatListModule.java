package com.moqod.android.chat.sample.chatlist.injection;

import com.moqod.android.chat.sample.chatlist.ChatListRouter;
import injection.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 17:10
 */
@Module
public class ChatListModule {

    private ChatListRouter mRouter;

    public ChatListModule(ChatListRouter router) {
        mRouter = router;
    }

    @Provides
    @ActivityScope
    public ChatListRouter provideChatListRouter() {
        return mRouter;
    }

}
