package org.catroid.catrobat.newui.utils;


import org.catroid.catrobat.newui.data.ListItem;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.io.FileInfo;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Utils {

    public static List<ListItem> getItemList() {
        List<ListItem> itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemList.add(new ListItem("Item ".concat(Integer.toString(i))));
        }
        return itemList;
    }

    public static LookInfo createNewLookInfo(String name) {
        FileInfo fileInfo = StorageHandler.createLookFileOnSD();
        return new LookInfo(name, fileInfo);
    }

    public static SoundInfo createNewSoundInfo(String name) {
        FileInfo fileInfo = StorageHandler.createSoundFileOnSD();
        return new SoundInfo(name, fileInfo);
    }

    public static String getUniqueName(String name, List<? extends ListItem> scope) {
        Set<String> nameSet = new HashSet<>();
        for (ListItem item : scope) {
            nameSet.add(item.getName());
        }

        return getUniqueName(name, nameSet);
    }

    private static String getUniqueName(String name, Set<String> nameSet) {
        String newName = name;
        int suffix = 1;
        while (nameSet.contains(newName)) {
            newName = name.concat(" ").concat(Integer.toString(suffix));
        }
        return newName;
    }
}
