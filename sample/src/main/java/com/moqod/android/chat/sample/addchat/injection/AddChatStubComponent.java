package com.moqod.android.chat.sample.addchat.injection;

import com.moqod.android.chat.sample.addchat.AddChatFragment;

import com.moqod.android.chat.sample.app.AppComponent;
import injection.ActivityScope;
import com.moqod.android.chat.sample.common.injection.ErrorHandlerModule;
import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {AddChatModule.class, ErrorHandlerModule.class})
public interface AddChatStubComponent {
    void inject(AddChatFragment fragment);
}