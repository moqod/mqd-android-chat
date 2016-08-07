package com.moqod.android.chat.data.db.table;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 31/05/16
 * Time: 13:17
 */
public class ChatTable {

    public static final String TABLE = "chats";

    public static final String COLUMN_JID = "jid";
    public static final String COLUMN_NAME = "name";

    public static String getCreateStatement() {
        return "CREATE TABLE " + TABLE + " (" +
                COLUMN_JID + " TEXT NOT NULL PRIMARY KEY, " +
                COLUMN_NAME + " TEXT" +
                ");";
    }

}
