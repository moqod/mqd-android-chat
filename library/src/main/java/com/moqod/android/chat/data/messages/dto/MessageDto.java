package com.moqod.android.chat.data.messages.dto;

import com.moqod.android.chat.data.db.table.MessagesTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 23/05/16
 * Time: 16:57
 */
@StorIOSQLiteType(table = MessagesTable.TABLE)
public class MessageDto {

    @StorIOSQLiteColumn(name = MessagesTable.COLUMN_CHAT_ID)
    public String chatId;
    @StorIOSQLiteColumn(name = MessagesTable.COLUMN_ID, key = true)
    public String id;
    @StorIOSQLiteColumn(name = MessagesTable.COLUMN_BODY)
    public String body;
    @StorIOSQLiteColumn(name = MessagesTable.COLUMN_STATE)
    public int state;
    @StorIOSQLiteColumn(name = MessagesTable.COLUMN_FROM_USER)
    public String fromUser;
    @StorIOSQLiteColumn(name = MessagesTable.COLUMN_TO_USER)
    public String toUser;
    @StorIOSQLiteColumn(name = MessagesTable.COLUMN_DATE)
    public long dateTime;
    @StorIOSQLiteColumn(name = MessagesTable.COLUMN_WEIGHT, ignoreNull = true)
    public Integer weight;

}
