package com.moqod.android.chat.di;

import com.moqod.android.chat.data.xmpp.XMPPService;
import com.moqod.android.chat.di.modules.DbModule;
import com.moqod.android.chat.di.modules.DomainModule;
import dagger.Subcomponent;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 07/08/16
 * Time: 23:32
 */
@Singleton
@Subcomponent(modules = {DbModule.class, DomainModule.class})
public interface ChatSingletonComponent {

    void inject(XMPPService xmppService);

}
