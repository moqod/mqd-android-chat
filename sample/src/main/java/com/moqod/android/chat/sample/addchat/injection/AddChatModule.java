package com.moqod.android.chat.sample.addchat.injection;

import com.moqod.android.chat.sample.addchat.AddChatRouter;
import com.moqod.android.chat.sample.common.ErrorHandler;
import dagger.Module;
import dagger.Provides;

@Module
public final class AddChatModule {

    private AddChatRouter mRouter;

    public AddChatModule(AddChatRouter router) {
        mRouter = router;
    }

    @Provides
    public AddChatRouter provideRouter() {
        return mRouter;
    }

}
