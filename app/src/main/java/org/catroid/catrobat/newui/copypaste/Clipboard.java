package org.catroid.catrobat.newui.copypaste;

import java.util.ArrayList;
import java.util.List;

public class Clipboard {
    public enum ItemType {
        UNDEFINED,
        PROJECT,
        SCENE,
        SPRITE,
        LOOK,
        SOUND
    }

    private static Clipboard instance;

    private ItemType mItemType = ItemType.UNDEFINED;
    private List<CopyPasteable> mItems = new ArrayList<>();

    public static synchronized Clipboard getInstance() {
        if (instance == null) {
            instance = new Clipboard();
        }

        return instance;
    }

    public void storeItemsForType(List<CopyPasteable> items, ItemType itemType) throws Exception {
        cleanupItems();
        storeItems(items);
        mItemType = itemType;
    }


    public void clear() throws Exception {
        cleanupItems();
        mItemType = ItemType.UNDEFINED;
    }

    public List<CopyPasteable> getItemsForType(ItemType type) {
        if (mItemType == type) {
            return mItems;
        } else {
            return null;
        }
    }

    public boolean containsItemsOfType(ItemType type) {
        return mItemType == type && mItems.size() > 0;
    }

    private void storeItems(List<CopyPasteable> items) throws Exception {
        for (CopyPasteable item : items) {
            CopyPasteable clonedItem = item.clone();
            clonedItem.prepareForClipboard();

            mItems.add(clonedItem);
        }
    }

    private void cleanupItems() throws Exception {
        for (CopyPasteable item : mItems) {
            item.cleanupFromClipboard();
        }
        mItems.clear();
    }

}
