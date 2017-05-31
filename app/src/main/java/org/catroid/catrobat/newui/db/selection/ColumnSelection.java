package org.catroid.catrobat.newui.db.selection;

public class ColumnSelection implements Selection {
    private String mColumn;
    private String mValue;

    public ColumnSelection(String column, String value) {
        mColumn = column;
        mValue = value;
    }

    public ColumnSelection(String column, long value) {
        mColumn = column;
        mValue = String.valueOf(value);
    }

    @Override
    public String getSelection(){
        return mColumn + " = ?";
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[]{mValue};
    }
}
