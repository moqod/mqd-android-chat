package com.moqod.android.chat.sample.common;

import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.sample.chatroom.MessageViewModel;
import com.moqod.android.utils.BetterSortedList;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 19/05/16
 * Time: 14:55
 */
public class ChatViewModel implements Serializable, BetterSortedList.SortedEntity<ChatViewModel> {

    private String mId;
    private String mName;
    private List<MessageModel>  mUnreadMessagesCount;
    private MessageViewModel mLastMessage;

    public ChatViewModel(String id, String name, List<MessageModel> unreadMessagesCount) {
        mId = id;
        mName = name;
        mUnreadMessagesCount = unreadMessagesCount;
    }

    public void setLastMessage(MessageViewModel lastMessage) {
        mLastMessage = lastMessage;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getUnreadMessagesCount() {
        return String.valueOf(mUnreadMessagesCount);
    }

    public boolean isHasUnreadMessages() {
        return mUnreadMessagesCount.size() != 0;
    }

    public List<MessageModel>  getUnreadMessagesCountRaw() {
        return mUnreadMessagesCount;
    }

    public String getLastMessage() {
        if (mLastMessage != null) {
            return mLastMessage.getBody();
        }
        return "";
    }

    public String getLastMessageTime() {
        if (mLastMessage != null) {
            return DateFormatter.messageDate(mLastMessage.getModel().getDate());
        }
        return "";
    }
    @Override
    public int compare(ChatViewModel entity) {
        if (areItemsTheSame(entity)) {
            return 0;
        }

        if (mLastMessage != null && entity.mLastMessage != null) {
            return Long.compare(mLastMessage.getModel().getDate().getTime(), entity.mLastMessage.getModel().getDate().getTime());
        } else if (mLastMessage != null) {
            return 1;
        } else if (entity.mLastMessage != null) {
            return -1;
        }
        return mName.compareTo(entity.mName);
    }

    @Override
    public boolean areItemsTheSame(ChatViewModel entity) {
        return mId == entity.getId();
    }

    @Override
    public boolean areContentsTheSame(ChatViewModel entity) {
        boolean hasSameLastMessage = true;
        if (mLastMessage != null && entity.mLastMessage != null) {
            hasSameLastMessage = mLastMessage.areContentsTheSame(entity.mLastMessage);
        } else if (mLastMessage != null || entity.mLastMessage != null) {
            hasSameLastMessage = false;
        }
        return mName.equals(entity.getName()) && mUnreadMessagesCount == entity.mUnreadMessagesCount && hasSameLastMessage;
    }

    @Override
    public String toString() {
        return "ChatViewModel{" +
                "id=" + mId +
                ", mLastMessage=" + mLastMessage +
                '}';
    }
}
