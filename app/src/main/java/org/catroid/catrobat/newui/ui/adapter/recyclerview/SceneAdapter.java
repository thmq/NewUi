package org.catroid.catrobat.newui.ui.adapter.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.fetchrequest.FetchRequest;
import org.catroid.catrobat.newui.db.brigde.DatabaseBridge;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;
import org.catroid.catrobat.newui.db.util.DataContract;

public class SceneAdapter extends DatabaseRecyclerViewAdapter<Scene> {
    private long mProjectId;

    public SceneAdapter(AppCompatActivity activity) {
        super(R.layout.list_item, activity);
    }

    public void startLoading(SceneBridge bridge, long projectId) {
        Log.d("SceneAdapter", "Starting to load scenes for "+ projectId);
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
    public void bindDataToViewHolder(Scene item, ViewHolder holder, boolean isSelected) {
        holder.mNameView.setText(item.getName());
    }
}
