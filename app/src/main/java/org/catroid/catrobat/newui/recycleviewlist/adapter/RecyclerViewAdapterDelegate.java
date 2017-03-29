package org.catroid.catrobat.newui.recycleviewlist.adapter;

import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapter;

public interface RecyclerViewAdapterDelegate<T> {
    void onSelectionChanged(RecyclerViewAdapter<T> adapter);
}
