package com.moqod.android.chat.domain.chats;

import com.moqod.android.chat.data.chats.LocalChatsRepository;
import com.moqod.android.chat.data.chats.criteria.EmptyCriteria;
import com.moqod.android.chat.domain.chats.model.ChatModel;
import rx.Observable;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 19/05/16
 * Time: 14:55
 */
public class ChatsInteractor {

    private LocalChatsRepository mChatsRepository;

    @Inject
    public ChatsInteractor(LocalChatsRepository chatsRepository) {
        mChatsRepository = chatsRepository;
    }

    public Observable<List<ChatModel>> getChats() {
        return mChatsRepository.get(EmptyCriteria.create()).toList();
    }

    public Observable<ChatModel> addChat(ChatModel chatModel) {
        return mChatsRepository.put(chatModel);
    }

    public Observable<ChatModel> removeChat(ChatModel chatModel) {
        return mChatsRepository.remove(chatModel);
    }

}
