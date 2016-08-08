package com.moqod.android.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/06/16
 * Time: 13:44
 */
public class LifecycleActivity extends AppCompatActivity {

    private List<Lifecycle> mLifecycleComponentList = new ArrayList<>();

    protected void addLifecycle(Lifecycle lifecycle) {
        mLifecycleComponentList.add(lifecycle);
    }

    protected void removeLifecycle(Lifecycle lifecycle) {
        mLifecycleComponentList.remove(lifecycle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).onResume();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).onPostResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).onDestroy();
        }
        mLifecycleComponentList.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        for (int i = 0, size = mLifecycleComponentList.size(); i < size; i++) {
            mLifecycleComponentList.get(i).saveState(outState);
        }
    }

}
