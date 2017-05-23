package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.data.bricks.BaseBrick;
import org.catroid.catrobat.newui.data.bricks.SetXBrick;
import org.catroid.catrobat.newui.ui.adapter.BrickAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScriptListFragment extends ListFragment implements TabFragment,
        BrickAdapter.SelectionListener {

    public static final String TAG = ScriptListFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number_script_list";

    private BrickAdapter adapter;
    private ActionMode actionMode;

    protected ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.script_context_menu, menu);

            setTabColor(ContextCompat.getColor(getActivity().getApplicationContext(),
                    R.color.colorActionMode));

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btnCopy:
                    copyItems((List<CopyPasteable>) (List<?>) adapter.getSelectedBricks());
                    adapter.clearSelection();
                    getActivity().invalidateOptionsMenu();
                    return true;
                case R.id.btnDelete:
                    removeItems();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            setTabColor(ContextCompat.getColor(getActivity().getApplicationContext(),
                    R.color.colorPrimary));

            actionMode = null;
        }
    };

    public static ScriptListFragment newInstance(int sectionNumber) {
        ScriptListFragment fragment = new ScriptListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View scriptListFragment = inflater.inflate(R.layout.fragment_list_view, container, false);
        setHasOptionsMenu(true);
        createAdapter();
        return scriptListFragment;
    }

    private void createAdapter() {
        List<BaseBrick> bricks = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SetXBrick brick = new SetXBrick();
            bricks.add(brick);
        }
        adapter = new BrickAdapter(getContext(), R.layout.list_item, bricks);
        setListAdapter(adapter);
        adapter.setSelectionListener(this);
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

    @Override
    public int getTabNameResource() {
        return R.string.tab_name_scripts;
    }

    public Clipboard.ItemType getItemType() {
        return Clipboard.ItemType.SCRIPT;
    }

    private boolean copyItems(List<CopyPasteable> selectedBricks) {
        boolean success;

        try {
            Clipboard.getInstance().storeItemsForType(selectedBricks, getItemType());
            success = true;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    private void pasteItems() {
        List<BaseBrick> bricks = (List<BaseBrick>) (List<?>) Clipboard.getInstance().getItemsForType(getItemType());

        if (bricks != null) {
            for (BaseBrick item : bricks) {
                try {
                    BaseBrick copiedItem = copyItem(item);
                    adapter.addItem(copiedItem);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private BaseBrick copyItem(BaseBrick brick) throws Exception {
        return brick.clone();
    }

    private void removeItems() {
        for (BaseBrick brick : adapter.getSelectedBricks()) {
            adapter.remove(brick);
        }

        adapter.clearSelection();
    }

    @Override
    public void onSelectionChanged() {
        List<BaseBrick> selectedItems = adapter.getSelectedBricks();

        if (selectedItems.isEmpty()) {
            if (actionMode != null) {
                actionMode.finish();
            }
        } else {
            if (actionMode == null) {
                actionMode = ((AppCompatActivity)
                        getActivity()).startSupportActionMode(actionModeCallback);
            }
        }
    }

    protected void setTabColor(int color) {
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            tabLayout.setBackgroundColor(color);
        }
    }

    @Override
    public void onAddButtonClicked() {

    }

    @Override
    public void clearSelection() {
        adapter.clearSelection();
    }
}
