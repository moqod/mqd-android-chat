package com.moqod.android.chat.domain.chats.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.moqod.android.chat.domain.common.BaseModel;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.utils.NullHelper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 19/05/16
 * Time: 14:55
 */
public class ChatModel extends BaseModel {

    private String mJid;
    private String mName;
    private List<MessageModel> mUnreadMessages;
    private MessageModel mLastMessage;

    public ChatModel(@NonNull String jid, String name) {
        mJid = jid;
        mName = name;
    }

    @NonNull
    public String getJid() {
        return mJid;
    }

    @NonNull
    public String getName() {
        return NullHelper.nonNull(mName);
    }

    public List<MessageModel>  getUnreadMessages() {
        return mUnreadMessages;
    }

    public void setUnreadMessages(List<MessageModel>  unreadMessages) {
        mUnreadMessages = unreadMessages;
    }

    @Nullable
    public MessageModel getLastMessage() {
        return mLastMessage;
    }

    public void setLastMessage(MessageModel lastMessage) {
        mLastMessage = lastMessage;
    }

}
