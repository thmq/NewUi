package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.db.brigde.ProjectBridge;
import org.catroid.catrobat.newui.ui.activity.ProjectActivity;
import org.catroid.catrobat.newui.ui.adapter.ProjectAdapter;

public class ProjectListFragment extends BaseRecyclerListFragment<Project> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) super.onCreateView(inflater, container, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        view.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public ProjectAdapter createAdapter() {
        ProjectActivity activity = (ProjectActivity) getActivity();

        ProjectBridge bridge = new ProjectBridge(activity);
        ProjectAdapter adapter = new ProjectAdapter(activity);

        adapter.startLoading(bridge);

        return adapter;
    }

    @Override
    protected String getItemName(Project item) {
        return item.getName();
    }

    @Override
    protected Clipboard.ItemType getItemType() {
        return Clipboard.ItemType.PROJECT;
    }

    @Override
    protected Project copyItem(Project item) throws Exception {
        return null;
    }

    @Override
    protected void cleanupItem(Project item) throws Exception {

    }

    @Override
    protected void renameItem(Project item, String itemName) {
        item.setName(itemName);
    }

    @Override
    protected Project createNewItem(String itemName) {
        Project p = new Project();

        p.setName(itemName);

        return p;
    }
}
