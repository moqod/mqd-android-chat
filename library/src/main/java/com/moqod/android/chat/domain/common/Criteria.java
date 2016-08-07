package com.moqod.android.chat.domain.common;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 04/08/16
 * Time: 18:45
 */
public interface Criteria<T> {
    T filter();
}
