package com.moqod.android.chat.sample.mainscreen;

import com.moqod.android.chat.sample.addchat.AddChatRouter;
import com.moqod.android.chat.sample.chatlist.ChatListRouter;
import com.moqod.android.chat.sample.chatroom.ChatRoomRouter;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 17:15
 */
public interface MainRouter extends ChatListRouter, AddChatRouter, ChatRoomRouter {
}
