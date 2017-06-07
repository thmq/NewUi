package org.catroid.catrobat.newui.db.selection;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class CompoundSelection implements Selection {
    private Selection[] mSelections;
    private String mGlue;

    protected CompoundSelection(@NonNull Selection[] selections, @NonNull String glue) {
        mSelections = selections;
        mGlue = " " + glue + " ";
    }

    @Override
    public String getSelection() {
        if (mSelections.length == 0) {
            return null;
        }

        StringBuilder selectionBuilder = new StringBuilder();

        selectionBuilder.append("(");
        for (int i = 0; i < mSelections.length; i++) {
            if (i > 0) {
                selectionBuilder.append(mGlue);
            }
            Selection sel = mSelections[i];
            selectionBuilder.append(sel.getSelection());
        }
        selectionBuilder.append(")");

        return selectionBuilder.toString();

    }

    @Override
    public String[] getSelectionArgs() {
        List<String> selectionArgs = new ArrayList<>();

        for (int i = 0; i < mSelections.length; i++) {
            Selection sel = mSelections[i];

            for (String selectionArg : sel.getSelectionArgs()) {
                selectionArgs.add(selectionArg);
            }
        }

        return selectionArgs.toArray(new String[selectionArgs.size()]);
    }
}
