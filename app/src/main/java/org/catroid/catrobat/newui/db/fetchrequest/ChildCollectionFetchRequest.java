package org.catroid.catrobat.newui.db.fetchrequest;


import org.catroid.catrobat.newui.db.selection.ColumnSelection;

public class ChildCollectionFetchRequest extends FetchRequest {
    private final String mSortOrder;

    public ChildCollectionFetchRequest(String parentIdColumn, long parentIdValue) {
        addSelection(new ColumnSelection(parentIdColumn, parentIdValue));
        mSortOrder = null;
    }

    public ChildCollectionFetchRequest(String parentIdColumn, long parentIdValue, String sortOrder) {
        addSelection(new ColumnSelection(parentIdColumn, parentIdValue));
        mSortOrder = sortOrder;
    }
}
