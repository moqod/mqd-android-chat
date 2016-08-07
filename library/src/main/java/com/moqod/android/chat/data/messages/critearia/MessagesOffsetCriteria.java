package com.moqod.android.chat.data.messages.critearia;

import com.moqod.android.chat.data.common.QueryCriteria;
import com.moqod.android.chat.data.db.table.MessagesTable;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 27/05/16
 * Time: 17:08
 */
public class MessagesOffsetCriteria extends QueryCriteria {

    public static MessagesOffsetCriteria create(String chatId, int offset, int quantity) {
        return new MessagesOffsetCriteria(chatId, offset, quantity);
    }

    private String mChatId;
    private int mOffset;
    private int mQuantity;

    private MessagesOffsetCriteria(String chatId, int offset, int quantity) {
        mChatId = chatId;
        mOffset = offset;
        mQuantity = quantity;
    }

    @Override
    public Query filter() {
        return Query.builder()
                .table(MessagesTable.TABLE)
                .where(MessagesTable.COLUMN_CHAT_ID + " = ?")
                .whereArgs(mChatId)
                .orderBy(MessagesTable.COLUMN_WEIGHT + " DESC")
                .limit(mOffset, mQuantity)
                .build();
    }
}
