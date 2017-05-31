package org.catroid.catrobat.newui.ui.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Sprite;
import org.catroid.catrobat.newui.db.brigde.DatabaseBridge;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.fetchrequest.FetchRequest;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.DatabaseRecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.ListViewHolder;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;


public class SpriteAdapter extends DatabaseRecyclerViewAdapter<Sprite> {
    private long mSceneId;

    public SpriteAdapter(AppCompatActivity activity) {
        super(R.layout.list_item, activity);
    }

    public void startLoading(DatabaseBridge<Sprite> bridge, long sceneId) {
        mSceneId = sceneId;
        super.startLoading(bridge);
    }

    @Override
    protected FetchRequest getFetchRequest() {
        return new ChildCollectionFetchRequest(DataContract.SpriteEntry.COLUMN_SCENE_ID, mSceneId);
    }

    @Override
    public void insertItem(Sprite item) {
        item.setSceneId(mSceneId);
        super.insertItem(item);
    }

    @Override
    public RecyclerViewHolder createViewHolder(View view) {
        return new ListViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(Sprite item, RecyclerViewHolder holder, boolean isSelected) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;

        listViewHolder.mNameView.setText(item.getName());
    }
}
