package com.moqod.android.chat.sample.chatroom;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.sample.common.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 20/05/16
 * Time: 17:07
 */
public class MessageMapper {

    @NonNull
    public static List<MessageViewModel> map(List<MessageModel> messageModels, @Nullable String selfUserId) {
        int size = messageModels.size();
        ArrayList<MessageViewModel> result = new ArrayList<>(size);

        MessageModel model;
        for (int i = 0; i < size; i++) {
            model = messageModels.get(i);

            result.add(map(model, selfUserId));
        }

        return result;
    }

    @NonNull
    public static MessageViewModel map(MessageModel model, @Nullable String selfUserId) {
        return new MessageViewModel(model.getStringId(), model.getWeight(),
                model.getState(), model.getDate(), model.getBody(),
                new UserViewModel(model.getFromUser()),
                (selfUserId != null && selfUserId.equals(model.getFromUser())), model);
    }

}
