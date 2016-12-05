package com.moqod.android.chat.domain.messages.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.moqod.android.chat.domain.common.BaseModel;
import com.moqod.android.chat.utils.NullHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 20/05/16
 * Time: 17:01
 */
public class MessageModel extends BaseModel {

    private String mId;
    private Integer mWeight;
    private @MessageState.State int mState;
    private Date mDate;
    private String mFromUser;
    private String mToUser;
    private String mBody;
    private String mChatId;
    private BaseMessageAttach mAttach;
    private boolean mOffline;

    public MessageModel(String id, Integer weight, @MessageState.State int state, Date date,
                        @NonNull String fromUser, @NonNull String toUser, String chatId, boolean offline) {
        super();
        mId = id;
        mWeight = weight;
        mState = state;
        mDate = date;
        mFromUser = fromUser;
        mToUser = toUser;
        mChatId = chatId;
        mOffline = offline;
    }

    public MessageModel(String id, Integer weight, @MessageState.State int state, Date date,
                        @NonNull String fromUser, @NonNull String toUser, String chatId) {
        super();
        mId = id;
        mWeight = weight;
        mState = state;
        mDate = date;
        mFromUser = fromUser;
        mToUser = toUser;
        mChatId = chatId;
    }

    public String getStringId() {
        return mId;
    }

    @NonNull
    public String getBody() {
        return NullHelper.nonNull(mBody);
    }

    public void setBody(String body) {
        mBody = body;
    }

    @NonNull
    public Date getDate() {
        if (mDate == null) {
            mDate = Calendar.getInstance().getTime();
        }
        return mDate;
    }

    public @MessageState.State int getState() {
        return mState;
    }

    public void setState(@MessageState.State int state) {
        mState = state;
    }

    @NonNull
    public String getFromUser() {
        return mFromUser;
    }

    @NonNull
    public String getToUser() {
        return mToUser;
    }

    @Nullable
    public BaseMessageAttach getAttach() {
        return mAttach;
    }

    public void setAttach(BaseMessageAttach messageAttach) {
        mAttach = messageAttach;
    }

    @NonNull
    public Integer getWeight() {
        if (mWeight == null) {
            mWeight = 0;
        }
        return mWeight;
    }

    public String getChatId() {
        return mChatId;
    }

    public boolean isOffline() {
        return mOffline;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "mId='" + mId + '\'' +
                ", mState=" + mState +
                "} " + super.toString();
    }
}
