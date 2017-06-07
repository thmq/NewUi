package org.catroid.catrobat.newui.db.fetchrequest;

import android.util.Log;

import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.db.selection.AndCompoundSelection;
import org.catroid.catrobat.newui.db.selection.Selection;

import java.util.ArrayList;
import java.util.List;

abstract public class FetchRequest {
    private boolean mIsDirty = true;
    private List<Selection> mSelections = new ArrayList<>();
    private AndCompoundSelection mSelection;
    private String mSortOrder;

    public void addSelection(Selection selection) {
        mIsDirty = true;
        mSelections.add(selection);
    }

    public String getSelection() {
        ensureCleanCompoundSelection();
        return mSelection.getSelection();
    }

    public String[] getSelectionArgs() {
        ensureCleanCompoundSelection();
        return mSelection.getSelectionArgs();
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(String sortOrder) {
        mSortOrder = sortOrder;
    }

    private void ensureCleanCompoundSelection() {
        if (mIsDirty) {
            mSelection = new AndCompoundSelection(mSelections.toArray(new Selection[mSelections.size()]));
            mIsDirty = false;
        }
    }

}
