package com.moqod.android.chat.data.xmpp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.moqod.android.chat.domain.messages.models.BaseMessageAttach;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.domain.messages.models.MessageState;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.delay.packet.DelayInformation;
import org.jivesoftware.smackx.json.packet.JsonPacketExtension;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 17/06/16
 * Time: 10:37
 */
class Mapper {
    @NonNull
    static Message map(MessageModel messageModel) {
        String to = messageModel.getToUser();
        Message.Type type = Message.Type.chat;

        Message message = new Message(to, messageModel.getBody());

        message.setType(type);
        message.setStanzaId(messageModel.getStringId());
        message.setFrom(messageModel.getFromUser());

        BaseMessageAttach attach = messageModel.getAttach();

        if (attach != null) {
            message.addExtension(new JsonPacketExtension(attach.getJson()));
        }
        return message;
    }

    @Nullable
    static MessageModel map(Message xmppMessage) {
        Date messageTime;
        DelayInformation delay = xmppMessage.getExtension(DelayInformation.ELEMENT, DelayInformation.NAMESPACE);
        if (delay != null) {
            messageTime = delay.getStamp();
        } else {
            messageTime = new Date();
        }

        String messageId = xmppMessage.getStanzaId();

        if (TextUtils.isEmpty(messageId)) {
            return null;
        }

        final String messageInfoJson;
        JsonPacketExtension jsonExtension = xmppMessage.getExtension(JsonPacketExtension.ELEMENT, JsonPacketExtension.NAMESPACE);
        if (jsonExtension != null) {
            messageInfoJson = jsonExtension.getJson();
        } else {
            messageInfoJson = null;
        }

        DelayInformation delayInformation = DelayInformation.from(xmppMessage);

        String from = TextUtils.split(xmppMessage.getFrom(), "/")[0];

        MessageModel messageModel = new MessageModel(messageId, null, MessageState.STATE_NEW, messageTime,
                from, xmppMessage.getTo(), from, delayInformation != null);
        messageModel.setBody(xmppMessage.getBody());

        return messageModel;
    }

}
