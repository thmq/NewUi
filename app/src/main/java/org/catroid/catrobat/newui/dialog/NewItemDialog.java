package org.catroid.catrobat.newui.dialog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.catroid.catrobat.newui.R;

public class NewItemDialog extends InputDialog {

    public static final String TAG = NewItemDialog.class.getSimpleName();
    private NewItemInterface newItemInterface;

    public static NewItemDialog newInstance(int title, int inputLabel, int positiveButton,
                                            int negativeButton, boolean allowEmptyInput) {
        NewItemDialog dialog = new NewItemDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, title);
        bundle.putInt(INPUT_LABEL, inputLabel);
        bundle.putInt(POSITIVE_BUTTON, positiveButton);
        bundle.putInt(NEGATIVE_BUTTON, negativeButton);
        bundle.putBoolean(ALLOW_EMPTY_INPUT, allowEmptyInput);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setNewItemInterface(NewItemInterface newItemInterface) {
        this.newItemInterface = newItemInterface;
    }

    protected View inflateLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_new_item, null);
    }

    protected boolean handlePositiveButtonClick() {
        String name = input.getText().toString().trim();

        if (newItemInterface.isNameValid(name)) {
            newItemInterface.addNewItem(name);
            return true;
        }

        input.setError(getString(R.string.error_invalid_item_name));
        return false;
    }

    public interface NewItemInterface {
        boolean isNameValid(String itemName);
        void addNewItem(String itemName);
    }
}
