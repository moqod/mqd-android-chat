package com.moqod.android.chat.domain.messages.models;

import android.support.annotation.IntDef;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 24/05/16
 * Time: 13:03
 */
public class MessageState {

    public static final int STATE_NEW = 1;
    public static final int STATE_READ = 2;
    public static final int STATE_SENDING = 3;
    public static final int STATE_SENT = 4;
    public static final int STATE_ERROR = 5;
    public static final int STATE_OUTGOING = 6;
    public static final int STATE_PREPARE = 7;

    @IntDef({STATE_NEW, STATE_READ, STATE_SENDING, STATE_SENT, STATE_ERROR, STATE_OUTGOING, STATE_PREPARE})
    public @interface State{}

}
