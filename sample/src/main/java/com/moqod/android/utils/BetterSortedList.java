package com.moqod.android.utils;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 14/04/16
 * Time: 13:44
 */
public class BetterSortedList<T> extends SortedList<T> {

    private Callback<T> mCallback;
    private ArrayList<T> itemsToRemove = new ArrayList<>();

    public BetterSortedList(Class<T> klass, Callback<T> callback) {
        super(klass, callback);
        mCallback = callback;
    }

    public BetterSortedList(Class<T> klass, Callback<T> callback, int initialCapacity) {
        super(klass, callback, initialCapacity);
        mCallback = callback;
    }

    public void updateAll(List<T> collection) {
        SortedList<T> oldData = this;
        int oldSize = oldData.size();
        int newSize = collection.size();

        ArrayList<T> newData = new ArrayList<>(collection);
        Collections.sort(newData, mCallback);

        if (oldSize > 0) {
            T oldItem;
            T newItem;
            for (int i = 0; i < oldSize; i++) {
                oldItem = oldData.get(i);

                boolean needRemove = true;
                for (int j = 0; j < newSize; j++) {
                    newItem = newData.get(j);

                    if (mCallback.areItemsTheSame(oldItem, newItem)) {
                        needRemove = false;
                        break;
                    }
                }

                if (needRemove) {
                    itemsToRemove.add(oldItem);
                }
            }
        }

        oldData.beginBatchedUpdates();
        T item;
        for (int i = 0, size = itemsToRemove.size(); i < size; i++) {
            item = itemsToRemove.get(i);
            oldData.remove(item);
        }
        itemsToRemove.clear();

        int oldIndex, newIndex;
        for (int i = 0; i < newSize; i++) {
            item = newData.get(i);
            newIndex = i;

            oldIndex = findSameItem(item);

            if (oldIndex > SortedList.INVALID_POSITION) {
                oldData.updateItemAt(oldIndex, item);
                if (oldIndex != newIndex) {
                    oldData.recalculatePositionOfItemAt(oldIndex);
                }
            } else {
                oldData.add(item);
            }
        }
        oldData.endBatchedUpdates();
    }

    private int findSameItem(T item) {
        for (int pos = 0, size = size(); pos < size; pos++) {
            if (mCallback.areItemsTheSame(get(pos), item)) {
                return pos;
            }
        }
        return INVALID_POSITION;
    }

    public static class BetterCallback<E extends SortedEntity> extends SortedListAdapterCallback<E> {

        public BetterCallback(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(E o1, E o2) {
            return o1.compare(o2);
        }

        @Override
        public boolean areContentsTheSame(E oldItem, E newItem) {
            return oldItem.areContentsTheSame(newItem);
        }

        @Override
        public boolean areItemsTheSame(E item1, E item2) {
            return item1.areItemsTheSame(item2);
        }
    }

    public interface SortedEntity<E extends SortedEntity> {
        int compare(E entity);
        boolean areItemsTheSame(E entity);
        boolean areContentsTheSame(E entity);
    }

}
