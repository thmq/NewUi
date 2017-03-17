package org.catroid.catrobat.newui.utils;


import org.catroid.catrobat.newui.data.BaseListItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Utils {

    public static String getUniqueName(String name, List<? extends BaseListItem> scope) {
        Set<String> nameSet = new HashSet<>();
        for (BaseListItem item : scope) {
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
