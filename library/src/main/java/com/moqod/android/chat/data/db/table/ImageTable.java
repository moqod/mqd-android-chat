package com.moqod.android.chat.data.db.table;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 31/05/16
 * Time: 13:55
 */
public class ImageTable {

    public static final String TABLE = "images";

    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_PHOTO_ID = "photo_id";
    public static final String COLUMN_DENSITY = "density";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_LOCAL = "local";

    public static String getCreateStatement() {
        return "CREATE TABLE " + TABLE + " (" +
                COLUMN_KEY + " TEXT, " +
                COLUMN_PHOTO_ID + " INTEGER, " +
                COLUMN_DENSITY + " INTEGER, " +
                COLUMN_LOCAL + " INTEGER, " +
                COLUMN_URI + " TEXT" +
                ");";
    }

}
