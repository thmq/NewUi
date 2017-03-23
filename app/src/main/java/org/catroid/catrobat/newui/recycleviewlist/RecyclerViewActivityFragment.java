package org.catroid.catrobat.newui.recycleviewlist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.ActionModeListener;
import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.ListItem;
import org.catroid.catrobat.newui.utils.Utils;

import java.util.List;

public class RecyclerViewActivityFragment extends Fragment implements RecyclerViewAdapterDelegate {

    ActionMode mActionMode;

    public static final String TAG = RecyclerViewActivityFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;

    private MenuItem mEditButton;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_view, container, false);

        List<ListItem> items = Utils.getItemList();
        mRecyclerViewAdapter = new RecyclerViewAdapter(items, R.layout.list_item);
        mRecyclerViewAdapter.addObserver(this);

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return mRecyclerView;
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
                    ActionModeListener.renameItem(mRecyclerViewAdapter.getSelectedItems().get(0));
                    mRecyclerViewAdapter.clearSelection();
                    return true;
                case R.id.btnCopy:
                    ActionModeListener.copyItems(mRecyclerViewAdapter.getSelectedItems());
                    mRecyclerViewAdapter.clearSelection();
                    return true;
                case R.id.btnDelete:
                    ActionModeListener.deleteItems(mRecyclerViewAdapter.getSelectedItems());
                    mRecyclerViewAdapter.clearSelection();
                    return true;

                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mRecyclerViewAdapter.clearSelection();
            mActionMode = null;
        }
    };

    @Override
    public void onSelectionChanged(RecyclerViewAdapter adapter) {
        List<ListItem> selectedItems = adapter.getSelectedItems();

        if (selectedItems.size() == 0) {
            if (mActionMode != null) {
                mActionMode.finish();
            }
        } else {
            if (mActionMode == null) {
                Log.d(TAG, "entering edit mode");
                mActionMode = getActivity().startActionMode(mActionModeCallback);
            }

            if (selectedItems.size() <= 1) {
                Log.d(TAG, "showing edit button");

                setEditButtonVisibility(true);
            } else {
                Log.d(TAG, "hiding edit button");

                setEditButtonVisibility(false);
            }
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
}
