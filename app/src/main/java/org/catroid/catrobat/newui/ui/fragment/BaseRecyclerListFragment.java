package org.catroid.catrobat.newui.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.data.ItemInfo;
import org.catroid.catrobat.newui.dialog.NewItemDialog;
import org.catroid.catrobat.newui.dialog.RenameItemDialog;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.ui.AddItemActivity;
import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapterDelegate;
import org.catroid.catrobat.newui.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public abstract class BaseRecyclerListFragment<T extends CopyPasteable> extends Fragment
        implements RecyclerViewAdapterDelegate<T>, NewItemDialog.NewItemInterface,
        RenameItemDialog.RenameItemInterface, Serializable {

    public static final String TAG = BaseRecyclerListFragment.class.getSimpleName();
    public static final int ADD_NEW_ITEM_REQUEST = 1;

    protected ActionMode mActionMode;
    protected RecyclerView mRecyclerView;
    protected MenuItem mEditButton;
    protected RecyclerViewAdapter<T> mRecyclerViewAdapter;
    protected ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);

            mEditButton = menu.findItem(R.id.btnEdit);

            setTabColor(ContextCompat.getColor(getActivity().getApplicationContext(),
                    R.color.colorActionMode));

            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btnEdit:
                    showItemRenameDialog();

                    return true;
                case R.id.btnCopy:
                    copyItems(mRecyclerViewAdapter.getSelectedItems());
                    mRecyclerViewAdapter.clearSelection();
                    getActivity().invalidateOptionsMenu();

                    return true;

                case R.id.btnDelete:
                    try {
                        removeItems(mRecyclerViewAdapter.getSelectedItems());
                    } catch (Exception e) {
                        Context context = getActivity().getApplicationContext();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    mRecyclerViewAdapter.clearSelection();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mRecyclerViewAdapter.clearSelection();
            setTabColor(ContextCompat.getColor(getActivity().getApplicationContext(),
                    R.color.colorPrimary));

            mActionMode = null;
        }
    };

    private List<BaseRecyclerListFragmentObserver> mObservers = new ArrayList<>();

    public static Fragment newInstance(int sectionNumber) {
        return null;
    }

    public abstract int getTabNameResource();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_view,
                container, false);

        mRecyclerViewAdapter = createAdapter();
        mRecyclerViewAdapter.setDelegate(this);

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return mRecyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.recycler_view_menu, menu);
        boolean enabled = Clipboard.getInstance().containsItemsOfType(getItemType());

        menu.getItem(0).setEnabled(enabled);
        menu.getItem(0).setVisible(enabled);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnPaste:
                pasteItems();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public abstract RecyclerViewAdapter<T> createAdapter();

    public void onAddButtonClicked() {
        ArrayList<String> names = new ArrayList<>();
        for(T i : mRecyclerViewAdapter.getItems()) {
            names.add(getItemName(i));
        }

        Intent intent = new Intent(getContext(), AddItemActivity.class);
        intent.putExtra("names_list", names);
        intent.putExtra("caller_tag", getString(getTabNameResource()));
        startActivityForResult(intent, ADD_NEW_ITEM_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NEW_ITEM_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            byte[] byteArray = data.getByteArrayExtra("image");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            PathInfoFile pathInfoFile = StorageHandler.createImage(bitmap, name);
            T item = createNewItem(name, pathInfoFile);
            addToList(item);
        }
    }

    private void showNewItemDialog() {
        NewItemDialog dialog = NewItemDialog.newInstance(
                R.string.dialog_new_name,
                R.string.dialog_item_name_label,
                R.string.create_item_primary_action,
                R.string.cancel,
                false
        );

        dialog.setNewItemInterface(this);
        dialog.show(getFragmentManager(), dialog.getTag());
    }

    private void showItemRenameDialog() {
        T item = mRecyclerViewAdapter.getSelectedItems().get(0);
        
        RenameItemDialog dialog = RenameItemDialog.newInstance(
                R.string.dialog_rename_item,
                R.string.dialog_item_name_label,
                R.string.dialog_rename_primary_action,
                R.string.cancel,
                false,
                getItemName(item)
        );

        dialog.setRenameItemInterface(this);
        dialog.show(getFragmentManager(), dialog.getTag());
    }

    protected abstract void addToList(T item);

    protected abstract String getItemName(T item);

    private boolean copyItems(List<T> items) {
        boolean success;

        try {
            Clipboard.getInstance().storeItemsForType((List<CopyPasteable>) items, getItemType());
            success = true;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    private void pasteItems() {
        List<T> items = (List<T>) Clipboard.getInstance().getItemsForType(getItemType());

        if (items != null) {
            for (T item : items) {
                try {
                    T copiedItem = copyItem(item);
                    mRecyclerViewAdapter.addItem(copiedItem);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract Clipboard.ItemType getItemType();

    protected abstract T copyItem(T item) throws Exception;

    private void removeItems(List<T> items) {
        for (T item : items) {
            try {
                cleanupItem(item);
                mRecyclerViewAdapter.removeItem(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void cleanupItem(T item) throws Exception;

    @Override
    public void onSelectionChanged(RecyclerViewAdapter<T> adapter) {
        List<T> selectedItems = adapter.getSelectedItems();

        if (selectedItems.isEmpty()) {
            if (mActionMode != null) {
                mActionMode.finish();
            }
        } else {
            if (mActionMode == null) {
                mActionMode = ((AppCompatActivity)
                        getActivity()).startSupportActionMode(mActionModeCallback);
            }

            boolean editButtonVisibility = selectedItems.size() <= 1;
            setEditButtonVisibility(editButtonVisibility);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        mEditButton = (MenuItem) v.findViewById(R.id.btnEdit);
    }

    private void setEditButtonVisibility(boolean visible) {

        if (mEditButton != null && mEditButton.isVisible() != visible) {
            mEditButton.setVisible(visible);
            getActivity().invalidateOptionsMenu();
        }
    }

    public void clearSelection() {
        mRecyclerViewAdapter.clearSelection();
    }

    @Override
    public boolean isNameValid(String itemName) {
        if (itemName != null) {
            if (itemName.length() > 0 &&
                    Utils.isItemNameUnique(itemName, mRecyclerViewAdapter.getItems())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewItem(String itemName) {
        T item = createNewItem(itemName);

        if (item != null) {
            mRecyclerViewAdapter.addItem(item);

            for (BaseRecyclerListFragmentObserver observer : mObservers) {
                observer.onNewItemAdded(this, item);
            }
        }
    }

    @Override
    public void renameItem(String itemName) {
        List<T> selectedItems = mRecyclerViewAdapter.getSelectedItems();

        if (selectedItems.size() == 1) {
            T item = mRecyclerViewAdapter.getSelectedItems().get(0);

            renameItem(item, itemName);

            mRecyclerViewAdapter.itemChanged(item);
            mRecyclerViewAdapter.clearSelection();
        }
    }

    protected abstract void renameItem(T item, String itemName);

    protected abstract T createNewItem(String itemName, PathInfoFile pathInfoFile);

    protected abstract T createNewItem(String itemName);

    protected void setTabColor(int color) {
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            tabLayout.setBackgroundColor(color);
        }
    }

    public void addObserver(BaseRecyclerListFragmentObserver observer) {
        mObservers.add(observer);
    }

    public void removeObserver(BaseRecyclerListFragmentObserver observer) {
        mObservers.remove(observer);
    }
}
