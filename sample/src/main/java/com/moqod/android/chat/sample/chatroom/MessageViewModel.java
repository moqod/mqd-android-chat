package com.moqod.android.chat.sample.chatroom;

import android.text.TextUtils;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.domain.messages.models.MessageState;
import com.moqod.android.chat.sample.common.DateFormatter;
import com.moqod.android.chat.sample.common.UserViewModel;
import com.moqod.android.utils.BetterSortedList;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 20/05/16
 * Time: 16:54
 */
public class MessageViewModel implements BetterSortedList.SortedEntity<MessageViewModel> {

    private String mId;
    private int mState;
    private Date mDate;
    private String mBody;
    private UserViewModel mUser;
    private boolean mSelf;
    private String mDateTime;
    private MessageModel mModel;
    private int mWeight;

    public MessageViewModel(String id, int weight, int state, Date date, String body, UserViewModel user, boolean self, MessageModel model) {
        mId = id;
        mWeight = weight;
        mState = state;
        mDate = date;
        mBody = body;
        mUser = user;
        mSelf = self;
        mModel = model;

        mDateTime = DateFormatter.messageDate(date);
    }

    public String getId() {
        return mId;
    }

    public String getBody() {
        return mBody;
    }

    public boolean isHasBody() {
        return !TextUtils.isEmpty(mBody);
    }

    public boolean isSelf() {
        return mSelf;
    }

    public String getDate() {
        return mDateTime;
    }

    public boolean isError() {
        return mState == MessageState.STATE_ERROR;
    }

    public MessageModel getModel() {
        return mModel;
    }

    public UserViewModel getUser() {
        return mUser;
    }

    public boolean isHasName() {
        return !TextUtils.isEmpty(mUser.getName());
    }

    public String getName() {
        return mUser.getName();
    }

    @Override
    public int compare(MessageViewModel entity) {
        if (areItemsTheSame(entity)) return 0;
        return Integer.compare(mWeight, entity.mWeight);
    }

    @Override
    public boolean areItemsTheSame(MessageViewModel entity) {
        return mId.equals(entity.getId());
    }

    @Override
    public boolean areContentsTheSame(MessageViewModel entity) {
        return mBody.equals(entity.getBody()) && mState == entity.mState;
    }

    @Override
    public String toString() {
        return "MessageViewModel{" +
                "id='" + mId + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mDateTime='" + mDateTime + '\'' +
                ", mBody='" + mBody + '\'' +
                ", mState='" + mState + '\'' +
                '}';
    }
}
