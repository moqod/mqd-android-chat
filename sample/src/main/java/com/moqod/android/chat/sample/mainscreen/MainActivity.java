package com.moqod.android.chat.sample.mainscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.moqod.android.chat.XMPPConfiguration;
import com.moqod.android.chat.data.xmpp.XMPPService;
import com.moqod.android.chat.sample.Configuration;
import com.moqod.android.chat.sample.R;
import com.moqod.android.chat.sample.addchat.AddChatFragment;
import com.moqod.android.chat.sample.addchat.injection.AddChatModule;
import com.moqod.android.chat.sample.app.App;
import com.moqod.android.chat.sample.chatlist.ChatListFragment;
import com.moqod.android.chat.sample.chatlist.injection.ChatListModule;
import com.moqod.android.chat.sample.chatroom.ChatRoomFragment;
import com.moqod.android.chat.sample.chatroom.di.ChatRoomModule;
import com.moqod.android.chat.sample.common.ErrorHandler;
import com.moqod.android.chat.sample.common.injection.ErrorHandlerModule;
import injection.HasComponent;
import com.moqod.android.chat.sample.mainscreen.di.DaggerMainActivityComponent;
import com.moqod.android.chat.sample.mainscreen.di.MainActivityComponent;
import com.moqod.android.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity implements HasComponent<MainActivityComponent> {

    private MainActivityComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ActivityUtils.addFragment(getSupportFragmentManager(), ChatListFragment.newInstance(), R.id.fragment_container);
        }

        XMPPService.run(this, new XMPPConfiguration(Configuration.XMPP_HOST, Configuration.XMPP_PORT, Configuration.XMPP_DOMAIN,
                Configuration.XMPP_RESOURCE, Configuration.XMPP_LOGIN, Configuration.XMPP_PASSWORD));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            XMPPService.stop(this);
        }
    }

    @Override
    public MainActivityComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerMainActivityComponent.builder()
                    .appComponent(App.get(this).getComponent())
                    .chatListModule(new ChatListModule(mRouter))
                    .addChatModule(new AddChatModule(mRouter))
                    .errorHandlerModule(new ErrorHandlerModule(mErrorHandler))
                    .chatRoomModule(new ChatRoomModule(mRouter, ""))
                    .build();
        }
        return mComponent;
    }

    private ErrorHandler mErrorHandler = new ErrorHandler() {
        @Override
        public void handleError(Throwable throwable) {
        }
    };

    private MainRouter mRouter = new MainRouter() {
        @Override
        public void onCloseChatRoom() {
            getSupportFragmentManager().popBackStack();
        }

        @Override
        public void openAddChat() {
            ActivityUtils.replaceFragment(getSupportFragmentManager(), AddChatFragment.newInstance(), R.id.fragment_container, true);
        }

        @Override
        public void openChatRoom(String id) {
            ActivityUtils.replaceFragment(getSupportFragmentManager(), ChatRoomFragment.newInstance(id), R.id.fragment_container, true);
        }

        @Override
        public void closeAddChat() {
            getSupportFragmentManager().popBackStack();
        }
    };

}
