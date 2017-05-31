package org.catroid.catrobat.newui.db.contentprovider;

import android.net.Uri;

abstract public class ContentDescription {
    int mIdentifier;

    int getIdentifier() {
        return mIdentifier;
    }
    void setIdentifier(int identifier) {
        mIdentifier = identifier;
    }

    abstract String getPath();
    abstract String getTableName();
    abstract Uri getItemsUri();
    abstract String getIdColumn();
}
