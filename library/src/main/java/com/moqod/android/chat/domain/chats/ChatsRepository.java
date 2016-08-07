package com.moqod.android.chat.domain.chats;

import com.moqod.android.chat.domain.chats.model.ChatModel;
import com.moqod.android.chat.domain.common.Criteria;
import rx.Observable;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 19/05/16
 * Time: 14:57
 */
public interface ChatsRepository {

    Observable<ChatModel> get(Criteria criteria);
    Observable<ChatModel> put(ChatModel chatModel);
    Observable<ChatModel> remove(ChatModel chatModel);

}
