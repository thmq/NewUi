package org.catroid.catrobat.newui.ui.adapter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.db.fetchrequest.FetchRequest;
import org.catroid.catrobat.newui.db.fetchrequest.RootCollectionFetchRequest;
import org.catroid.catrobat.newui.db.selection.ColumnSelection;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.DatabaseRecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.GridViewHolder;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;

import static org.catroid.catrobat.newui.ui.adapter.ProjectAdapter.ProjectScope;

public class ProjectAdapter extends DatabaseRecyclerViewAdapter<Project> {
    private static final String TAG = ProjectAdapter.class.getSimpleName();
    public ProjectAdapter(AppCompatActivity context) {
        super(R.layout.project_item, context);
    }

    public enum ProjectScope {
        ALL,
        FAVORITES,
        RECENT
    }

    public ProjectScope mCurrentScope = ProjectScope.ALL;

    public void updateScope(ProjectScope scope) {
        mCurrentScope = scope;
        restartLoader();
    }

    @Override
    protected FetchRequest getFetchRequest() {
        FetchRequest fetchRequest = new RootCollectionFetchRequest();

        Log.d(TAG, "Fetch Request created");

        switch (mCurrentScope) {
            case ALL:
                Log.d(TAG, "Using all scope");
                break;

            case FAVORITES:
                fetchRequest.addSelection(new ColumnSelection(DataContract.ProjectEntry.COLUMN_FAVORITE, "1"));
                Log.d(TAG, "Using fav scope");
                break;

            case RECENT:
                Log.d(TAG, "Using recent scope");
                fetchRequest.setSortOrder(DataContract.ProjectEntry.COLUMN_LAST_ACCESS + " DESC");
                break;
        }

        return fetchRequest;
    }

    @Override
    public RecyclerViewHolder createViewHolder(View view) {
        return new GridViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(final Project project, RecyclerViewHolder holder, boolean isSelected) {
        GridViewHolder viewHolder = (GridViewHolder) holder;

        viewHolder.mImageView.getLayoutParams().height = Constants.PROJECT_IMAGE_SIZE;
        viewHolder.mImageView.getLayoutParams().width = Constants.PROJECT_IMAGE_SIZE;

        // FIXME: Use project thumbnail!
        // viewHolder.mImageView.setImageBitmap(project.getThumbnail());
        viewHolder.mImageView.setImageResource(R.drawable.blue_test_pic);

        viewHolder.mTextView.setWidth(Constants.PROJECT_IMAGE_SIZE);
        viewHolder.mTextView.setBackgroundColor(Color.BLACK);
        viewHolder.mTextView.getBackground().setAlpha(123);
        viewHolder.mTextView.setText(project.getName());

        if (project.getFavorite()) {
            viewHolder.mFavoriteView.setImageResource(R.drawable.favorite_white_selected);
        } else {
            viewHolder.mFavoriteView.setImageResource(R.drawable.favorite_white_empty);
        }

        viewHolder.mFavoriteView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                project.setFavorite(!project.getFavorite());
                mBridge.update(project);
            }
        });
    }
}
