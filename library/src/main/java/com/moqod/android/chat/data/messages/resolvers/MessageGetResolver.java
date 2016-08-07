package com.moqod.android.chat.data.messages.resolvers;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.moqod.android.chat.data.messages.dto.MessageDto;
import com.moqod.android.chat.data.messages.dto.MessageDtoStorIOSQLiteGetResolver;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/06/16
 * Time: 16:19
 */
public class MessageGetResolver extends GetResolver<MessageModel> {

    private final ThreadLocal<StorIOSQLite> storIOSQLiteFromPerformGet = new ThreadLocal<StorIOSQLite>();
    private final MessageDtoStorIOSQLiteGetResolver mMessageGetResolver;

    public MessageGetResolver() {
        mMessageGetResolver = new MessageDtoStorIOSQLiteGetResolver();
    }

    @NonNull
    @Override
    public MessageModel mapFromCursor(@NonNull Cursor cursor) {
        MessageDto messageDto = mMessageGetResolver.mapFromCursor(cursor);
        return MessageMapper.map(messageDto);
    }

    @NonNull
    @Override
    public Cursor performGet(@NonNull StorIOSQLite storIOSQLite, @NonNull RawQuery rawQuery) {
        storIOSQLiteFromPerformGet.set(storIOSQLite);
        return storIOSQLite.lowLevel().rawQuery(rawQuery);
    }

    @NonNull
    @Override
    public Cursor performGet(@NonNull StorIOSQLite storIOSQLite, @NonNull Query query) {
        storIOSQLiteFromPerformGet.set(storIOSQLite);
        return storIOSQLite.lowLevel().query(query);
    }

}
