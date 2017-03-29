package org.catroid.catrobat.newui.recycleviewlist.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecyclerViewMultiSelectionManager<T> {
    private Set<T> mSelectedItems;

    public RecyclerViewMultiSelectionManager() {
        mSelectedItems = new HashSet<T>();
    }

    public List<T> getSelectedItems() {
        return new ArrayList<>(mSelectedItems);
    }

    public void setSelected(T item, boolean selected) {
        if (selected) {
            mSelectedItems.add(item);
        } else {
            mSelectedItems.remove(item);
        }
    }

    public boolean getSelected(T item) {
        return mSelectedItems.contains(item);
    }

    public void clearSelection() {
        mSelectedItems.clear();
    }

    public boolean isSelectable(T item) {
        return true;
    }

    public void toggleSelected(T item) {
        setSelected(item, !getSelected(item));
    }

}
