package com.moqod.android.chat.domain.chats;

import com.moqod.android.chat.data.chats.LocalChatsRepository;
import com.moqod.android.chat.data.chats.criteria.EmptyCriteria;
import com.moqod.android.chat.data.messages.critearia.MessageStateCriteria;
import com.moqod.android.chat.data.messages.critearia.MessagesOffsetCriteria;
import com.moqod.android.chat.domain.chats.model.ChatModel;
import com.moqod.android.chat.domain.messages.MessagesRepository;
import com.moqod.android.chat.domain.messages.models.MessageState;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

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
    private MessagesRepository mMessagesRepository;

    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private PublishSubject<Object> mNotificationSubject = PublishSubject.create();


    @Inject
    public ChatsInteractor(LocalChatsRepository chatsRepository, MessagesRepository messagesRepository) {
        mChatsRepository = chatsRepository;
        mMessagesRepository = messagesRepository;
    }

    public Observable<List<ChatModel>> getChats() {
        return mChatsRepository.get(EmptyCriteria.create())
                .doOnSubscribe(() -> mSubscriptions.clear())
                .doOnNext(chatModel -> {
                    Subscription subscription = mMessagesRepository.notifyUpdates(chatModel.getJid())
                            .filter(messageModel -> {
                                int state = messageModel.getState();
                                return state == MessageState.STATE_NEW || state == MessageState.STATE_OUTGOING ||
                                        state == MessageState.STATE_SENDING || state == MessageState.STATE_READ;
                            })
                            .subscribe(messageModel -> mNotificationSubject.onNext(null));
                    mSubscriptions.add(subscription);
                })
                .flatMap(chatModel -> mMessagesRepository.get(MessageStateCriteria.create(chatModel.getJid(), MessageState.STATE_NEW))
                        .map(messageModels -> {
                            chatModel.setUnreadMessages(messageModels.size());
                            return chatModel;
                        })
                        .defaultIfEmpty(chatModel)
                )
                .flatMap(chatModel -> mMessagesRepository.get(MessagesOffsetCriteria.create(chatModel.getJid(), 0, 1))
                        .map(messageModels -> {
                            if (!messageModels.isEmpty()) {
                                chatModel.setLastMessage(messageModels.get(0));
                            }
                            return chatModel;
                        })
                        .defaultIfEmpty(chatModel)
                )
                .toList()
                .repeatWhen(observable -> observable.flatMap(o -> mNotificationSubject.take(1)));
    }

    public Observable<ChatModel> addChat(ChatModel chatModel) {
        return mChatsRepository.put(chatModel);
    }

    public Observable<ChatModel> removeChat(ChatModel chatModel) {
        return mChatsRepository.remove(chatModel);
    }

    public Observable<Void> clear() {
        return mChatsRepository.clear();
    }

}
