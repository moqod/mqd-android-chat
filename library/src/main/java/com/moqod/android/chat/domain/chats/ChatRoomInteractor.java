package com.moqod.android.chat.domain.chats;

import android.support.annotation.NonNull;
import com.moqod.android.chat.data.chats.criteria.ChatByIdCriteria;
import com.moqod.android.chat.data.messages.critearia.MessageStateCriteria;
import com.moqod.android.chat.data.messages.critearia.MessagesOffsetCriteria;
import com.moqod.android.chat.domain.chats.model.ChatModel;
import com.moqod.android.chat.domain.messages.MessagesRepository;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.domain.messages.models.MessageState;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 20/05/16
 * Time: 17:01
 */
public class ChatRoomInteractor {

    private ChatsRepository mLocalChatsRepository;
    private MessagesRepository mMessagesRepository;

    private PublishSubject<Object> mNotificationSubject = PublishSubject.create();
    private Subscription mSubscription;

    private Subject<ChatModel, ChatModel> mInitSubject = new SerializedSubject<>(PublishSubject.create());

    @Inject
    public ChatRoomInteractor(ChatsRepository localChatsRepository, MessagesRepository messagesRepository) {
        mLocalChatsRepository = localChatsRepository;
        mMessagesRepository = messagesRepository;
    }

    public Observable<ChatModel> initWithChatId(String chatId) {
        return mLocalChatsRepository.get(ChatByIdCriteria.create(chatId))
                .take(1)
                .doOnNext(mInitSubject::onNext);
    }

    // TODO: 26/05/16 remove chatId parameter
    public Observable<List<MessageModel>> getMessages(String chatId, int offset, int quantity) {
        return mMessagesRepository.get(MessagesOffsetCriteria.create(chatId, offset, quantity))
                .flatMap(markAsRead())
                .delaySubscription(mInitSubject);
    }

    public Observable<List<MessageModel>> getNewMessages(String chatId) {
        return mMessagesRepository.get(MessageStateCriteria.create(chatId, MessageState.STATE_NEW,
                MessageState.STATE_ERROR, MessageState.STATE_OUTGOING, MessageState.STATE_SENDING, MessageState.STATE_PREPARE))
                .doOnSubscribe(() -> {
                    if (mSubscription != null) {
                        mSubscription.unsubscribe();
                    }
                    mSubscription = mMessagesRepository.notifyUpdates(chatId)
                            .filter(messageModel -> {
                                int state = messageModel.getState();
                                return (state == MessageState.STATE_NEW || state == MessageState.STATE_ERROR ||
                                        state == MessageState.STATE_OUTGOING || state == MessageState.STATE_SENDING ||
                                        state == MessageState.STATE_PREPARE
                                );
                            })
                            .subscribe(messageModel -> mNotificationSubject.onNext(messageModel));
                })
                .flatMap(markAsRead())
                .repeatWhen(observable -> observable.flatMap(o -> mNotificationSubject.take(1)))
                .delaySubscription(mInitSubject);
    }

    public Observable<MessageModel> sendTextMessage(String text, String fromUser) {
        return mInitSubject.take(1)
                .flatMap(chatModel -> mMessagesRepository.put(MessageMapper.map(text, chatModel, fromUser)));
    }

    public Observable<MessageModel> resendMessage(MessageModel model) {
        model.setState(MessageState.STATE_OUTGOING);
        return mMessagesRepository.put(model);
    }

    @NonNull
    private Func1<List<MessageModel>, Observable<? extends List<MessageModel>>> markAsRead() {
        return messageModels -> Observable.from(messageModels)
                .flatMap(messageModel -> {
                    if (messageModel.getState() == MessageState.STATE_NEW) {
                        messageModel.setState(MessageState.STATE_READ);
                        return mMessagesRepository.put(messageModel);
                    }
                    return Observable.just(messageModel);
                })
                .toList();
    }

}
