package com.moqod.android.chat.data.messages.resolvers;

import android.support.annotation.NonNull;
import com.moqod.android.chat.data.db.table.MessagesTable;
import com.moqod.android.chat.data.messages.dto.MessageDto;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/06/16
 * Time: 16:18
 */
public class MessagePutResolver extends PutResolver<MessageModel> {

    final Set<String> affectedTables = new HashSet<>(1);

    {
        affectedTables.add(MessagesTable.TABLE);
    }

    @NonNull
    @Override
    public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull MessageModel object) {
        ArrayList<Object> objects = new ArrayList<>();

        MessageDto dto = MessageMapper.map(object);
        objects.add(dto);

        storIOSQLite
                .put()
                .objects(objects)
                .prepare()
                .executeAsBlocking();

        return PutResult.newUpdateResult(objects.size(), affectedTables);
    }

}
