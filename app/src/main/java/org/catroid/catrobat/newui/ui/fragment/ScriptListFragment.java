package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.bricks.BaseBrick;
import org.catroid.catrobat.newui.data.bricks.SetXBrick;
import org.catroid.catrobat.newui.ui.adapter.BrickAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScriptListFragment extends ListFragment implements TabFragment {

    private static final String ARG_SECTION_NUMBER = "section_number_script_list";
    BrickAdapter adapter;

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
        SetXBrick setXBrick = new SetXBrick();
        bricks.add(setXBrick);
        adapter = new BrickAdapter(getContext(), R.layout.list_item , bricks);
        setListAdapter(adapter);
    }

    @Override
    public int getTabNameResource() {
        return R.string.tab_name_scripts;
    }

    @Override
    public void onAddButtonClicked() {

    }

    @Override
    public void clearSelection() {

    }
}
