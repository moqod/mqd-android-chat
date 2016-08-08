package com.moqod.android.utils;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 17:18
 */
public class ActivityUtils {

    public static void addFragment(FragmentManager fm, Fragment fragment, @IdRes int container) {
        fm.beginTransaction()
                .add(container, fragment, null)
                .commit();
    }

    public static void replaceFragment(FragmentManager fm, Fragment fragment, @IdRes int container, boolean addToBackStack) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container, fragment, null);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
