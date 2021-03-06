package org.catroid.catrobat.newui.ui.fragment;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;

public interface BaseRecyclerListFragmentDelegate<T extends CopyPasteable> {
    void onItemClicked(BaseRecyclerListFragment<T> fragment, T item);
}
