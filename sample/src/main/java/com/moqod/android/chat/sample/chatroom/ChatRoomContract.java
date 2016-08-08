package com.moqod.android.chat.sample.chatroom;


import com.moqod.android.chat.sample.common.ChatViewModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 18/04/16
 * Time: 16:41
 */
public interface ChatRoomContract {

    interface View {
        void updateChatUi(ChatViewModel chatViewModel);
        void updateMessagesUi(List<MessageViewModel> newMessages);
    }

    interface EventListener {
        void closeChatRoom();
        void onSendTextMessage(String text);
        void onResendMessage(MessageViewModel model);
        void onLoadMore(int page);
    }

}
