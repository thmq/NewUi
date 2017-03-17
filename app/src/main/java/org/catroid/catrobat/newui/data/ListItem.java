package org.catroid.catrobat.newui.data;


public class ListItem {

    private String name;
    private String filePath;

    public ListItem(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ListItem)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        ListItem listItem = (ListItem) obj;

        return listItem.name.equals(this.name) && listItem.filePath.equals(this.filePath);
    }
}
