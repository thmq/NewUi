package org.catroid.catrobat.newui;

import android.util.Log;

import org.catroid.catrobat.newui.data.ListItem;

import java.util.List;

public final class ActionModeListener {

    private static final String NUM_ITEMS = "selected item cnt: ";
    private static final String DELETE = "item to delete: ";
    private static final String COPY = "item to copy: ";
    private static final String RENAME = "item to rename: ";
    private static final String BACKPACK = "item to pack: ";

    public boolean deleteItems(List<? extends ListItem> selectedItems) {
        Log.i(NUM_ITEMS, Integer.toString(selectedItems.size()));
        for (ListItem listItem : selectedItems) {
            Log.i(DELETE, listItem.getName());
        }
        return true;
    }

    public boolean copyItems(List<? extends ListItem> selectedItems) {
        Log.i(NUM_ITEMS, Integer.toString(selectedItems.size()));
        for (ListItem listItem : selectedItems) {
            Log.i(COPY, listItem.getName());
        }
        return true;
    }

    public void renameItem(ListItem selectedItem) {
        Log.i(RENAME, selectedItem.getName());
    }

    public boolean packItems(List<? extends ListItem> selectedItems) {
        Log.i(NUM_ITEMS, Integer.toString(selectedItems.size()));
        for (ListItem listItem : selectedItems) {
            Log.i(BACKPACK, listItem.getName());
        }
        return true;
    }
}
