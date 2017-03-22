package org.catroid.catrobat.newui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.catroid.catrobat.newui.R;

public class NewItemDialog extends AppCompatDialogFragment {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private NewItemInterface newItemInterface;
    private EditText input;

    public NewItemDialog newInstance(int title, int content) {
        NewItemDialog dialog = new NewItemDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, title);
        bundle.putInt(CONTENT, content);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setNewItemInterface(NewItemInterface newItemInterface) {
        this.newItemInterface = newItemInterface;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getArguments().getInt(TITLE));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_item, null);

        builder.setView(view);
        builder.setMessage(getArguments().getInt(CONTENT));

        input = (EditText) view.findViewById(R.id.input);

        builder.setPositiveButton(R.string.create_new_item, null);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onCancel(dialogInterface);
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                showKeyboard();
                Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (handlePositiveButtonClick()) {
                            dismiss();
                        }
                    }
                });
            }
        });

        return alertDialog;
    }

    private boolean handlePositiveButtonClick() {
        String name = input.getText().toString().trim();

        if (newItemInterface.isNameValid(name)) {
            newItemInterface.addNewItem(name);
            return true;
        }

        input.setError(getString(R.string.error_invalid_item_name));
        return false;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        dismiss();
    }

    protected void showKeyboard() {
        if (input.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public interface NewItemInterface {

        boolean isNameValid(String itemName);
        void addNewItem(String itemName);
    }
}
