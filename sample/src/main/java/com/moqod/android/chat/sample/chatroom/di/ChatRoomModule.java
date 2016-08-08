package com.moqod.android.chat.sample.chatroom.di;

import com.moqod.android.chat.sample.chatroom.ChatRoomRouter;
import injection.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 26/07/16
 * Time: 19:02
 */
@Module
public class ChatRoomModule {

    private ChatRoomRouter mRouter;
    private String mUserId;

    public ChatRoomModule(ChatRoomRouter router, String userId) {
        mRouter = router;
        mUserId = userId;
    }

    @Provides
    @ActivityScope
    public ChatRoomRouter provideChatRoomRouter() {
        return mRouter;
    }

    @Provides
    @ActivityScope
    @UserId
    public String provideUserId() {
        return mUserId;
    }

}
