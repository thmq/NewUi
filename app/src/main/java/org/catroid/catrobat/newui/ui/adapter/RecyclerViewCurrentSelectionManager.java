package org.catroid.catrobat.newui.ui.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RecyclerViewCurrentSelectionManager<T> {
    private List<T> mCurrentSelection = new ArrayList<>();
    private List<T> mNewSelection = new ArrayList<>();
    private List<T> mChanges = new ArrayList<>();

    public void setCurrentSelection(List<T> currentSelection) {
        this.mCurrentSelection = currentSelection;
        updateChanges();
    }

    public void setNewSelection(List<T> newSelection) {
        this.mNewSelection = newSelection;
        updateChanges();
    }

    private void updateChanges() {
        List<T> changes = new ArrayList<>();

        Set<T> removedItems = new HashSet<>(mCurrentSelection);
        Set<T> addedItems = new HashSet<>(mNewSelection);

        removedItems.removeAll(mNewSelection);
        addedItems.removeAll(mCurrentSelection);

        changes.addAll(removedItems);
        changes.addAll(addedItems);

        this.mChanges = changes;
    }

    public boolean getWasChanged(T item) {
        return mChanges.contains(item);
    }
}
