package org.catroid.catrobat.newui.db.fetchrequest;


public class ChildCollectionFetchRequest extends FetchRequest {
    private final String mParentIdColumn;
    private final long mParentIdValue;
    private final String mSortOrder;

    public ChildCollectionFetchRequest(String parentIdColumn, long parentIdValue) {
        mParentIdColumn = parentIdColumn;
        mParentIdValue = parentIdValue;
        mSortOrder = null;
    }

    public ChildCollectionFetchRequest(String parentIdColumn, long parentIdValue, String sortOrder) {
        mParentIdColumn = parentIdColumn;
        mParentIdValue = parentIdValue;
        mSortOrder = sortOrder;
    }
    
    @Override
    public String getSelection() {
        return mParentIdColumn + " = ?";
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[] {String.valueOf(mParentIdValue)};
    }

    @Override
    public String getSortOrder() {
        return mSortOrder;
    }
}
