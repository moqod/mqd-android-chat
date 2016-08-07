package com.moqod.android.chat.data.chats.criteria;

import com.moqod.android.chat.data.common.QueryCriteria;
import com.moqod.android.chat.data.db.table.ChatTable;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 31/05/16
 * Time: 17:20
 */
public class ChatByIdCriteria extends QueryCriteria {

    public static ChatByIdCriteria create(String id) {
        return new ChatByIdCriteria(id);
    }

    private String mId;

    private ChatByIdCriteria(String id) {
        mId = id;
    }

    @Override
    public Query filter() {
        return Query.builder()
                .table(ChatTable.TABLE)
                .where(ChatTable.COLUMN_JID + " = ?")
                .whereArgs(mId)
                .build();
    }

    public String getId() {
        return mId;
    }

    @Override
    public String toString() {
        return "ChatByIdCriteria{" +
                "mId=" + mId +
                "} " + super.toString();
    }

}
