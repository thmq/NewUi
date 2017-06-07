package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.db.brigde.ProjectBridge;
import org.catroid.catrobat.newui.ui.activity.ProjectActivity;
import org.catroid.catrobat.newui.ui.adapter.ProjectAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;

public class ProjectListFragment extends BaseRecyclerListFragment<Project> {

    private Animation mSlideOutRight;
    private Animation mSlideOutLeft;
    private Animation mSlideInLeft;
    private Animation mSlideInRight;

    private ProjectActivity mProjectActivity;
    private MenuItem mInfoButtonItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) super.onCreateView(inflater, container, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        view.setLayoutManager(layoutManager);

        setupSlideAnimations();
        setupRecyclerViewScrollListener();

        return view;
    }

    private void setupSlideAnimations() {
        mSlideOutRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        mSlideOutLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);

        mSlideInLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
        mSlideInRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
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


    public void animateSlideRightToScope(ProjectAdapter.ProjectScope scope) {
        performSlideAnmiation(mSlideOutLeft, mSlideInRight, scope);
    }


    public void animateSlideLeftToScope(ProjectAdapter.ProjectScope scope) {
        performSlideAnmiation(mSlideOutRight, mSlideInLeft, scope);
    }

    private void performSlideAnmiation(final Animation outAnim, final Animation inAnim, final ProjectAdapter.ProjectScope scope) {
        final ProjectAdapter adapter = (ProjectAdapter) mRecyclerViewAdapter;

        mRecyclerView.startAnimation(outAnim);

        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                adapter.updateScope(scope);
                mRecyclerView.startAnimation(inAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void setProjectActivity(ProjectActivity activity) {
        mProjectActivity = activity;
    }

    private void setupRecyclerViewScrollListener() {
        final ProjectListFragment fragment = this;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mProjectActivity.onProjectListFragmentScrolled(fragment, dx, dy);
            }
        });
    }
    
    @Override
    public int getContextMenuResource() {
        return R.menu.context_menu_project;
    }

    @Override
    public void onMenuInflatedForActionMode(ActionMode mode, Menu menu) {
        super.onMenuInflatedForActionMode(mode, menu);
        mInfoButtonItem = menu.findItem(R.id.btnInfo);
    }

    @Override
    public boolean onContextMenuActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.btnInfo) {
            Project project = mRecyclerViewAdapter.getSelectedItems().get(0);

            mProjectActivity.showInfoForProject(project);

            return true;
        } else {
            return super.onContextMenuActionItemClicked(mode, item);
        }
    }

    @Override
    public void onSelectionChanged(RecyclerViewAdapter<Project> adapter) {
        super.onSelectionChanged(adapter);

        Log.d(TAG, "selection changed");
        setContextMenuItemVisibility(mInfoButtonItem, adapter.getSelectedItems().size() == 1);
    }
}
