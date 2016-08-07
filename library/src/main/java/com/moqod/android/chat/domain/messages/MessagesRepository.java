package com.moqod.android.chat.domain.messages;

import com.moqod.android.chat.domain.common.Criteria;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import rx.Observable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 21/05/16
 * Time: 23:26
 */
public interface MessagesRepository {

    Observable<List<MessageModel>> get(Criteria criteria);
    Observable<MessageModel> notifyUpdates(String chatId);
    Observable<MessageModel> notifyUpdates();
    Observable<MessageModel> put(MessageModel message);

}
