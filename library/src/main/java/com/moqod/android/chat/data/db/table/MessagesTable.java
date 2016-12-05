package com.moqod.android.chat.data.db.table;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 23/05/16
 * Time: 17:04
 */
public class MessagesTable {

    public static final String TABLE = "messages";

    public static final String COLUMN_CHAT_ID = "chat_id";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_FROM_USER = "from_user";
    public static final String COLUMN_TO_USER = "to_user";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_OFFLINE = "offline";

    public static String getCreateStatement() {
        return "CREATE TABLE " + TABLE + " (" +
                COLUMN_CHAT_ID + " INTEGER NOT NULL, " +
                COLUMN_ID + " TEXT NOT NULL, " +
                COLUMN_BODY + " TEXT, " +
                COLUMN_STATE + " INTEGER, " +
                COLUMN_FROM_USER + " TEXT NOT NULL, " +
                COLUMN_TO_USER + " TEXT NOT NULL, " +
                COLUMN_DATE + " INTEGER, " +
                COLUMN_OFFLINE + " INTEGER, " +
                COLUMN_WEIGHT + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                ");";
    }

}
