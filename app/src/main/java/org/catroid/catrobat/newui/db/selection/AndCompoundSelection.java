package org.catroid.catrobat.newui.db.selection;

public class AndCompoundSelection extends CompoundSelection {
    public AndCompoundSelection(Selection[] selections) {
        super(selections, "AND");
    }
}
