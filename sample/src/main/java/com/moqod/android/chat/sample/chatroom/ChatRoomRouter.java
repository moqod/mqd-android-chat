package com.moqod.android.chat.sample.chatroom;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 26/07/16
 * Time: 18:58
 */
public interface ChatRoomRouter {

    void onCloseChatRoom();
    void onUserProfileClicked(int userId);
    void onVideoClicked(String uri);
    void onEditChatClicked(int chatId);
    void onChatClicked(int chatId);

}
