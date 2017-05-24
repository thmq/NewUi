package org.catroid.catrobat.newui.db.fetchrequest;

abstract public class FetchRequest {
    public abstract String getSelection();
    public abstract String[] getSelectionArgs();
    public abstract String getSortOrder();
}
