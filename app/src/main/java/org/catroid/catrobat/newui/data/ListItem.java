package org.catroid.catrobat.newui.data;


import org.catroid.catrobat.newui.R;

public class ListItem {

    private String name;
    private int imageRes;

    public ListItem(String name) {
        this.name = name;
        this.imageRes = R.drawable.ic_face_black_24dp;
    }

    public String getName() {
        return name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setName(String name) {
        this.name = name;
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

        return listItem.name.equals(this.name);
    }
}
