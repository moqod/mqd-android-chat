package com.moqod.android.chat.data.chats;

import com.moqod.android.chat.data.chats.dto.ChatDto;
import com.moqod.android.chat.domain.chats.model.ChatModel;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 06/08/16
 * Time: 23:37
 */
class ChatMapper {

    static ChatModel map(ChatDto dto) {
        return new ChatModel(dto.jid, dto.name);
    }

    public static ChatDto map(ChatModel model) {
        ChatDto dto = new ChatDto();
        dto.jid = model.getJid();
        dto.name = model.getName();
        return dto;
    }
}
