package org.catroid.catrobat.newui.ui.adapter;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.fetchrequest.FetchRequest;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.DatabaseRecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.ListViewHolder;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;

public class SceneAdapter extends DatabaseRecyclerViewAdapter<Scene> {
    private long mProjectId;

    private static String TAG = SceneAdapter.class.getSimpleName();
    public SceneAdapter(AppCompatActivity activity) {
        super(R.layout.list_item, activity);
    }

    public void startLoading(SceneBridge bridge, long projectId) {
        Log.d(TAG, "Starting to load scenes for project #"+ projectId);
        mProjectId = projectId;

        startLoading(bridge);
    }

    @Override
    protected FetchRequest getFetchRequest() {
        if (mProjectId != 0) {
            return new ChildCollectionFetchRequest(DataContract.SceneEntry.COLUMN_PROJECT_ID, mProjectId);
        } else {
            return null;
        }
    }

    @Override
    public void insertItem(Scene item) {
        item.setProjectId(mProjectId);
        super.insertItem(item);
    }

    @Override
    public RecyclerViewHolder createViewHolder(View view) {
        return new ListViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(Scene item, RecyclerViewHolder holder, boolean isSelected) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        listViewHolder.mNameView.setText(item.getName());
    }
}
