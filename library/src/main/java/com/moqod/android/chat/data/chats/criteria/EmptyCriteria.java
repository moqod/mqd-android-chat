package com.moqod.android.chat.data.chats.criteria;

import com.moqod.android.chat.data.common.QueryCriteria;
import com.moqod.android.chat.data.db.table.ChatTable;
import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 07/08/16
 * Time: 23:46
 */
public class EmptyCriteria extends QueryCriteria {

    public static EmptyCriteria create() {
        return new EmptyCriteria();
    }

    private EmptyCriteria() {
    }

    @Override
    public Query filter() {
        return Query.builder().table(ChatTable.TABLE).build();
    }
}
