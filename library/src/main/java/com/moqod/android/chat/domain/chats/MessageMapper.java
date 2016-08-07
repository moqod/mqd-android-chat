package com.moqod.android.chat.domain.chats;

import android.support.annotation.NonNull;
import com.moqod.android.chat.domain.chats.model.ChatModel;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.domain.messages.models.MessageState;
import org.jivesoftware.smack.packet.id.StanzaIdUtil;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 03/06/16
 * Time: 16:26
 */
class MessageMapper {

    @NonNull
    static MessageModel map(String text, ChatModel currentChat, String fromUser) {
        MessageModel messageModel = new MessageModel(nextMessageId(), null,
                MessageState.STATE_OUTGOING, Calendar.getInstance().getTime(), fromUser, currentChat.getJid(), currentChat.getJid());
        messageModel.setBody(text);
        return messageModel;
    }

    static String nextMessageId() {
        return StanzaIdUtil.newStanzaId();
    }

}
