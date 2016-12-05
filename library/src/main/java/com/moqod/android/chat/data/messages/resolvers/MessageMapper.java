package com.moqod.android.chat.data.messages.resolvers;

import android.support.annotation.NonNull;
import com.moqod.android.chat.data.messages.dto.MessageDto;
import com.moqod.android.chat.domain.messages.models.MessageModel;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 31/05/16
 * Time: 14:40
 */
class MessageMapper {

    @NonNull
    static MessageDto map(MessageModel model) {
        MessageDto dto = new MessageDto();
        dto.chatId = model.getChatId();
        dto.id = model.getStringId();
        dto.body = model.getBody();
        dto.dateTime = model.getDate().getTime();
        dto.state = model.getState();
        dto.fromUser = model.getFromUser();
        dto.toUser = model.getToUser();
        dto.offline = model.isOffline();

        return dto;
    }

    @NonNull
    static MessageModel map(MessageDto dto) {
        MessageModel model = new MessageModel(dto.id, dto.weight, dto.state, new Date(dto.dateTime), dto.fromUser, dto.toUser, dto.chatId, dto.offline);
        model.setBody(dto.body);
        return model;
    }
}
