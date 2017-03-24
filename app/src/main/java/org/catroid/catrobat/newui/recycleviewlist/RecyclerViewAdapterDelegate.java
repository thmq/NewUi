package org.catroid.catrobat.newui.recycleviewlist;

import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapter;

public interface RecyclerViewAdapterDelegate {
    void onSelectionChanged(RecyclerViewAdapter adapter);
}
