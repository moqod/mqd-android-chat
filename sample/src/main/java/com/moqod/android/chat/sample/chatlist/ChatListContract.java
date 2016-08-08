package com.moqod.android.chat.sample.chatlist;

import com.moqod.android.chat.sample.common.ChatViewModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 15:14
 */
public interface ChatListContract {

    interface View {
        void showChatList(List<ChatViewModel> list);
        void showLoading();
    }

    interface EventListener {
        void onAddClicked();
        void onChatClicked(ChatViewModel viewModel);
    }

}
