package com.moqod.android.chat.di.modules;

import com.moqod.android.chat.data.chats.dto.ChatDto;
import com.moqod.android.chat.data.chats.dto.ChatDtoSQLiteTypeMapping;
import com.moqod.android.chat.data.db.DbOpenHelper;
import com.moqod.android.chat.data.messages.dto.MessageDto;
import com.moqod.android.chat.data.messages.dto.MessageDtoSQLiteTypeMapping;
import com.moqod.android.chat.data.messages.resolvers.MessageDeleteResolver;
import com.moqod.android.chat.data.messages.resolvers.MessageGetResolver;
import com.moqod.android.chat.data.messages.resolvers.MessagePutResolver;
import com.moqod.android.chat.di.Internal;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 31/05/16
 * Time: 14:02
 */
@Module
public class DbModule {

    @Provides
    @Singleton
    @Internal
    public StorIOSQLite provideStorIOSQLite(DbOpenHelper openHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(openHelper)
                .addTypeMapping(MessageDto.class, new MessageDtoSQLiteTypeMapping())
                .addTypeMapping(ChatDto.class, new ChatDtoSQLiteTypeMapping())
                .addTypeMapping(MessageModel.class, SQLiteTypeMapping.<MessageModel>builder()
                        .putResolver(new MessagePutResolver())
                        .getResolver(new MessageGetResolver())
                        .deleteResolver(new MessageDeleteResolver())
                        .build()
                )
                .build();
    }

}
