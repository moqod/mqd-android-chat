package com.moqod.android.chat.sample.addchat;

public interface AddChatMvpContract {
    interface View {
    }

    interface EventListener {
        void onCreateClicked(CreateChatViewModel viewModel);
        void onCloseClicked();
    }
}