package org.catroid.catrobat.newui.recycleviewlist.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.ListItem;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.dialog.NewItemDialog;
import org.catroid.catrobat.newui.dialog.RenameItemDialog;
import org.catroid.catrobat.newui.io.FileInfo;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.recycleviewlist.adapter.LookAdapter;
import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapterDelegate;
import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LookListFragment extends Fragment implements RecyclerViewAdapterDelegate, NewItemDialog.NewItemInterface {

    public static final String TAG = LookListFragment.class.getSimpleName();

    private ActionMode mActionMode;
    private RecyclerView mRecyclerView;

    private MenuItem mEditButton;
    private LookAdapter mLookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_view, container, false);

        List<ListItem> items = Utils.getItemList();
        mLookAdapter = new LookAdapter(new ArrayList<LookInfo>(), R.layout.list_item);
        mLookAdapter.setDelegate(this);

        mRecyclerView.setAdapter(mLookAdapter);

        return mRecyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // FIXME: THIS SHOULD NOT BE PRESENT HERE.
        // Instead this should reside in the activity itself
        // and a callback (or something similar) should be used.
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (fab == null) {
            return;
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewDialog();
            }
        });
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);

            mEditButton = menu.findItem(R.id.btnEdit);

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
                    // ActionModeListener.renameItem(mLookAdapter.getSelectedItems().get(0));
                    mLookAdapter.clearSelection();
                    return true;
                case R.id.btnCopy:
                    copyItems(mLookAdapter.getSelectedItems());
                    mLookAdapter.clearSelection();
                    return true;
                case R.id.btnDelete:
                    removeItems(mLookAdapter.getSelectedItems());
                    mLookAdapter.clearSelection();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mLookAdapter.clearSelection();
            mActionMode = null;
        }
    };

    private void copyItems(List<LookInfo> items) {
        for (LookInfo item : items) {
            try {
                String name = Utils.getUniqueLookName(item.getName(), mLookAdapter.getItems());
                FileInfo fileInfo = StorageHandler.copyFileInfo(item.getFileInfo());

                LookInfo newLookInfo = new LookInfo(name, fileInfo);
                mLookAdapter.addItem(newLookInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void removeItems(List<LookInfo> items) {
        for (LookInfo item : items) {
            try {
                item.cleanup();
                mLookAdapter.removeItem(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSelectionChanged(RecyclerViewAdapter adapter) {
        List<ListItem> selectedItems = adapter.getSelectedItems();

        if (selectedItems.isEmpty()) {
            if (mActionMode != null) {
                mActionMode.finish();
            }
        } else {
            if (mActionMode == null) {
                mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(mActionModeCallback);
            }

            boolean editButtonVisibility = selectedItems.size() <= 1;
            setEditButtonVisibility(editButtonVisibility);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        mEditButton = (MenuItem) v.findViewById(R.id.btnEdit);
    }

    private void setEditButtonVisibility(boolean visible) {

        if (mEditButton != null && mEditButton.isVisible() != visible) {
            mEditButton.setVisible(visible);
            getActivity().invalidateOptionsMenu();
        }

    }

    private void showNewDialog() {
        NewItemDialog dialog = NewItemDialog.newInstance(
                R.string.dialog_create_item,
                R.string.create_new_item,
                R.string.create_new_item,
                R.string.cancel,
                false
        );

        dialog.setNewItemInterface(this);
        dialog.show(getFragmentManager(), dialog.getTag());
    }

    @Override
    public boolean isNameValid(String itemName) {
        return true;
    }

    @Override
    public void addNewItem(String itemName) {
        String uniqItemName = Utils.getUniqueLookName(itemName, mLookAdapter.getItems());

        FileInfo fileInfo = createLookImage(uniqItemName);
        LookInfo item = new LookInfo(uniqItemName, fileInfo);

        mLookAdapter.addItem(item);
    }

    private FileInfo createLookImage(String name) {
        StorageHandler.setupDirectoryStructure();

        String dir = Utils.getImageDirectory().getAbsolutePath();
        File file;
        try {
            file = StorageHandler.getUniqueFile("look.png", dir);
        } catch (Exception e) {
            // Should never be hit.
            // Exception is thrown if there are INT_MAX duplicate filenames
            // So, if an error occurs, we've got other problems ;)

            e.printStackTrace();
            return null;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.d(TAG, "File not created: " + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        InputStream is = getResources().openRawResource(R.raw.bg_260);
        Bitmap bmp = BitmapFactory.decodeStream(is);

        try {
            StorageHandler.exportBitmapToFile(bmp, file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new FileInfo(Utils.getImageDirectory(), file.getName());
    }
}
