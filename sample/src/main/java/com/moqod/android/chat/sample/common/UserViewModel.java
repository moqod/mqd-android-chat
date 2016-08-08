package com.moqod.android.chat.sample.common;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 11:52
 */
public class UserViewModel {

    private String mName;

    public UserViewModel(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
