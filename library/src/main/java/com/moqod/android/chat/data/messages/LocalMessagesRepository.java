package com.moqod.android.chat.data.messages;

import com.moqod.android.chat.data.common.QueryCriteria;
import com.moqod.android.chat.di.Internal;
import com.moqod.android.chat.domain.common.Criteria;
import com.moqod.android.chat.domain.messages.MessagesRepository;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import rx.Observable;
import rx.subjects.PublishSubject;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 21/05/16
 * Time: 23:30
 */
public class LocalMessagesRepository implements MessagesRepository {

    private StorIOSQLite mDb;

    private PublishSubject<MessageModel> mChangeNotificationSubject = PublishSubject.create();

    @Inject
    public LocalMessagesRepository(@Internal StorIOSQLite db) {
        mDb = db;
    }

    @Override
    public Observable<List<MessageModel>> get(Criteria criteria) {
        QueryCriteria queryCriteria = (QueryCriteria) criteria;
        return mDb
                .get()
                .listOfObjects(MessageModel.class)
                .withQuery(queryCriteria.filter())
                .prepare()
                .asRxObservable()
                .take(1);
    }

    @Override
    public Observable<MessageModel> notifyUpdates(String chatId) {
        return mChangeNotificationSubject.filter(messageModel -> messageModel.getChatId().equals(chatId));
    }

    @Override
    public Observable<MessageModel> notifyUpdates() {
        return mChangeNotificationSubject;
    }

    @Override
    public Observable<MessageModel> put(MessageModel message) {
        return mDb.put()
                .object(message)
                .prepare()
                .asRxObservable()
                .flatMap(putResult -> Observable.just(message))
                .doOnNext(messageModel -> mChangeNotificationSubject.onNext(message));
    }

}
