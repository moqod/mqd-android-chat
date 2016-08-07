package com.moqod.android.chat.data.messages.critearia;

import com.moqod.android.chat.data.common.QueryCriteria;
import com.moqod.android.chat.data.db.table.MessagesTable;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 27/05/16
 * Time: 17:14
 */
public class MessageStateCriteria extends QueryCriteria {

    public static MessageStateCriteria create(String chatId, int... states) {
        return new MessageStateCriteria(chatId, states);
    }

    private String mChatId;
    private int[] mStates;

    private MessageStateCriteria(String chatId, int... states) {
        mChatId = chatId;
        mStates = states;
    }

    @Override
    public Query filter() {
        ArrayList<Object> whereArgs = new ArrayList<>();
        whereArgs.add(mChatId);

        StringBuilder where = new StringBuilder(MessagesTable.COLUMN_CHAT_ID + " = ? AND (");
        for (int i = 0; i < mStates.length; i++) {
            if (i > 0) {
                where.append(" OR ");
            }

            where.append(MessagesTable.COLUMN_STATE + " = ?");
            whereArgs.add(mStates[i]);
        }
        where.append(")");

        return Query.builder()
                .table(MessagesTable.TABLE)
                .where(where.toString())
                .whereArgs(whereArgs)
                .orderBy(MessagesTable.COLUMN_DATE + " DESC")
                .build();
    }

}
