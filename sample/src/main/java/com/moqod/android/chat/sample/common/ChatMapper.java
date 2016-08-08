package com.moqod.android.chat.sample.common;

import android.support.annotation.NonNull;
import com.moqod.android.chat.domain.chats.model.ChatModel;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.sample.chatroom.MessageMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 15:35
 */
public class ChatMapper {

    public static List<ChatViewModel> map(List<ChatModel> models) {
        int size = models.size();
        ArrayList<ChatViewModel> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            result.add(map(models.get(i)));
        }

        return result;
    }

    @NonNull
    public static ChatViewModel map(ChatModel model) {
        ChatViewModel viewModel = new ChatViewModel(model.getJid(), model.getName(), model.getUnreadMessages());

        MessageModel lastMessage = model.getLastMessage();
        if (lastMessage != null) {
            viewModel.setLastMessage(MessageMapper.map(lastMessage, null));
        }
        return viewModel;
    }

}
