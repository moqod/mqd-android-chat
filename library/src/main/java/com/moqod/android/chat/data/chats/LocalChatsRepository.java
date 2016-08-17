package com.moqod.android.chat.data.chats;

import com.moqod.android.chat.data.chats.dto.ChatDto;
import com.moqod.android.chat.data.common.QueryCriteria;
import com.moqod.android.chat.di.Internal;
import com.moqod.android.chat.domain.chats.ChatsRepository;
import com.moqod.android.chat.domain.chats.model.ChatModel;
import com.moqod.android.chat.domain.common.Criteria;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 30/05/16
 * Time: 20:42
 */
@Singleton
public class LocalChatsRepository implements ChatsRepository {

    private StorIOSQLite mStorIO;

    @Inject
    public LocalChatsRepository(@Internal StorIOSQLite storIO) {
        mStorIO = storIO;
    }

    @Override
    public Observable<ChatModel> get(Criteria criteria) {
        QueryCriteria queryCriteria = (QueryCriteria) criteria;
        return mStorIO
                .get()
                .listOfObjects(ChatDto.class)
                .withQuery(queryCriteria.filter())
                .prepare()
                .asRxObservable()
                .take(1)
                .flatMap(Observable::from)
                .map(ChatMapper::map);
    }

    @Override
    public Observable<ChatModel> put(ChatModel chatModel) {
        return Observable.just(chatModel)
                .flatMap(model -> {
                    ChatDto dto = ChatMapper.map(model);
                    return mStorIO
                            .put()
                            .object(dto)
                            .prepare()
                            .asRxObservable()
                            .map(putResult -> chatModel);
                });
    }

    @Override
    public Observable<ChatModel> remove(ChatModel chatModel) {
        return Observable.just(chatModel)
                .flatMap(model -> {
                    ChatDto dto = ChatMapper.map(model);
                    return mStorIO
                            .delete()
                            .object(dto)
                            .prepare()
                            .asRxObservable()
                            .map(putResult -> chatModel);
                });
    }

}
