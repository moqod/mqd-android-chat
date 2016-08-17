package com.moqod.android.chat.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.moqod.android.chat.data.db.table.ChatTable;
import com.moqod.android.chat.data.db.table.ImageTable;
import com.moqod.android.chat.data.db.table.MessagesTable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 23/05/16
 * Time: 17:00
 */
@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    public static final String DB_NAME = "messages_database.db";

    @Inject
    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MessagesTable.getCreateStatement());
        db.execSQL(ChatTable.getCreateStatement());
        db.execSQL(ImageTable.getCreateStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
