package org.catroid.catrobat.newui.ui.recyclerview.adapter;

public interface RecyclerViewAdapterDelegate<T> {
    void onSelectionChanged(RecyclerViewAdapter<T> adapter);
    void onItemClicked(RecyclerViewAdapter<T> adapter, T item);
}
