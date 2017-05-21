package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.bricks.BaseBrick;
import org.catroid.catrobat.newui.data.bricks.SetXBrick;
import org.catroid.catrobat.newui.ui.adapter.BrickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScriptListFragment extends ListFragment implements TabFragment,
        BrickAdapter.SelectionListener {

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
                    copyItems(adapter.getSelectedBricks());
                    adapter.clearSelection();
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
    public int getTabNameResource() {
        return R.string.tab_name_scripts;
    }

    private boolean copyItems(Set<BaseBrick> selectedBricks) {
        //TODO: add to copypasteable
        return true;
    }

    private void removeItems() {
        for (BaseBrick brick : adapter.getSelectedBricks()) {
            adapter.remove(brick);
        }

        adapter.clearSelection();
    }

    @Override
    public void onSelectionChanged() {
        Set<BaseBrick> selectedItems = adapter.getSelectedBricks();

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
