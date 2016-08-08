package com.moqod.android.chat.sample.common;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 04/04/16
 * Time: 20:27
 */
public interface BasePresenter<V> {

    void attachView(V view);
    void detachView();

}
