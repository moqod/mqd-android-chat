package com.moqod.android.chat.data.chats.dto;

import com.moqod.android.chat.data.db.table.ChatTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 31/05/16
 * Time: 13:10
 */
@StorIOSQLiteType(table = ChatTable.TABLE)
public class ChatDto {

    @StorIOSQLiteColumn(name = ChatTable.COLUMN_JID, key = true)
    public String jid;
    @StorIOSQLiteColumn(name = ChatTable.COLUMN_NAME)
    public String name;

}
