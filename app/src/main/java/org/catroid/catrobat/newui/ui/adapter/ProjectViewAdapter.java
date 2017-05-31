package org.catroid.catrobat.newui.ui.adapter;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.db.brigde.ProjectBridge;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewAdapter extends ArrayAdapter implements LoaderManager.LoaderCallbacks<List<Project>> {

    protected class ListItemViewHolder {
        protected View mView;
        protected RelativeLayout mBackground;
        protected TextView mTextView;
        protected ImageView mImageView;
        protected ImageView mFavoriteView;

        public ListItemViewHolder(View projectView) {
            mView = projectView;
            mBackground = (RelativeLayout) projectView.findViewById(R.id.project_background);
            mImageView = (ImageView) projectView.findViewById(R.id.project_image_view);
            mTextView = (TextView) projectView.findViewById(R.id.project_title_view);
            mFavoriteView = (ImageView) projectView.findViewById(R.id.favorite_project_image_view);
        }
    }

    protected LayoutInflater inflater;

    private ProjectBridge mProjectsBridge;

    private ArrayList<Project> objects;
    private ProjectViewAdapterDelegate mDelegate;
    private AppCompatActivity mActivity;

    public ProjectViewAdapter(AppCompatActivity activity, int textViewResourceId,
                              ArrayList<Project> objects) {
        super(activity, textViewResourceId, objects);

        mActivity = activity;
        inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setupDatabaseBridge();
        startLoader();
    }


    private void startLoader() {
        mActivity.getSupportLoaderManager().restartLoader(0, null, this);
    }

    private void setupDatabaseBridge() {
        mProjectsBridge = new ProjectBridge(mActivity);
        mProjectsBridge.registerContentObserver(new ContentObserver(new Handler()){
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);

                startLoader();
            }
        });
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View projectView = convertView;
        final ListItemViewHolder viewHolder;
        final Project project = (Project) getItem(position);

        if (projectView == null) {
            projectView = inflater.inflate(R.layout.project_item, null);
            viewHolder = new ListItemViewHolder(projectView);
            projectView.setTag(viewHolder);
        } else {
            viewHolder = (ListItemViewHolder) projectView.getTag();
        }

        viewHolder.mImageView.getLayoutParams().height = Constants.PROJECT_IMAGE_SIZE;
        viewHolder.mImageView.getLayoutParams().width = Constants.PROJECT_IMAGE_SIZE;

        // FIXME: Use project thumbnail!
        // viewHolder.mImageView.setImageBitmap(project.getThumbnail());
        viewHolder.mImageView.setImageResource(R.drawable.blue_test_pic);


        viewHolder.mTextView.setWidth(Constants.PROJECT_IMAGE_SIZE);
        viewHolder.mTextView.setBackgroundColor(Color.BLACK);
        viewHolder.mTextView.getBackground().setAlpha(123);
        viewHolder.mTextView.setText(project.getInfoText());

        if (project.getFavorite()) {
            viewHolder.mFavoriteView.setImageResource(R.drawable.favorite_white_selected);
        } else {
            viewHolder.mFavoriteView.setImageResource(R.drawable.favorite_white_empty);
        }

        viewHolder.mFavoriteView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                project.setFavorite(!project.getFavorite());
                mProjectsBridge.update(project);
            }
        });

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyProjectClicked(project);
            }
        });

        return projectView;
    }

    private void notifyProjectClicked(Project project) {
        if (mDelegate != null) {
            mDelegate.onProjectClick(this, project);
        }
    }

    public void setDelegate(ProjectViewAdapterDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public Loader<List<Project>> onCreateLoader(int id, Bundle args) {
        Loader<List<Project>> asyncLoader = new AsyncTaskLoader<List<Project>>(getContext()) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                forceLoad();
            }

            @Override
            public List<Project> loadInBackground() {
                return mProjectsBridge.findAll();
            }
        };

        return asyncLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Project>> loader, List<Project> projects) {
        clear();
        addAll(projects);
    }

    @Override
    public void onLoaderReset(Loader<List<Project>> loader) {
        clear();
    }
}
