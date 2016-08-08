package com.moqod.android.chat.sample.chatroom.di;

import com.moqod.android.chat.sample.chatroom.ChatRoomRouter;
import com.moqod.android.chat.sample.common.ActivityScope;
import com.moqod.android.chat.sample.common.ErrorHandler;
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
    private ErrorHandler mErrorHandler;

    public ChatRoomModule(ChatRoomRouter router, String userId, ErrorHandler errorHandler) {
        mRouter = router;
        mUserId = userId;
        mErrorHandler = errorHandler;
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

    @Provides
    @ActivityScope
    public ErrorHandler provideErrorHandler() {
        return mErrorHandler;
    }

}
