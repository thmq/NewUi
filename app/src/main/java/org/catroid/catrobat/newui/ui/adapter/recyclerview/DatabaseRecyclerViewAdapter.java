package org.catroid.catrobat.newui.ui.adapter.recyclerview;


import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.catroid.catrobat.newui.db.fetchrequest.FetchRequest;
import org.catroid.catrobat.newui.db.brigde.DatabaseBridge;
import org.catroid.catrobat.newui.db.brigde.PersistableRecord;

import java.util.List;

abstract public class DatabaseRecyclerViewAdapter<T extends PersistableRecord> extends RecyclerViewAdapter<T> implements LoaderManager.LoaderCallbacks<List<T>> {

    private static final String TAG = "DBRecyclerViewAdapter";
    private final AppCompatActivity mContext;
    private DatabaseBridge<T> mBridge;
    private ContentObserver mContentObserver;

    public DatabaseRecyclerViewAdapter(int itemLayout, AppCompatActivity context) {
        super(itemLayout);
        
        mContext = context;
    }

    public void startLoading(DatabaseBridge<T> bridge) {
        mBridge = bridge;

        setupContentObserver();
        restartLoader();
    }

    private void setupContentObserver() {
        Log.d(TAG, "Setting content observer for " + mBridge.toString());
        mContentObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                restartLoader();
            }
        };
        
        mBridge.registerContentObserver(mContentObserver);
    }

    protected abstract FetchRequest getFetchRequest();

    protected void restartLoader() {
        Log.d(TAG, "Restarting loader");
        mContext.getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<List<T>> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List<T>> loader = new AsyncTaskLoader<List<T>>(mContext) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                forceLoad();
            }

            @Override
            public List<T> loadInBackground() {
                FetchRequest fetchRequest = getFetchRequest();

                Log.d(TAG, "Starting to load...");

                if (fetchRequest != null) {
                    return mBridge.findAll(fetchRequest);
                } else {
                    Log.d(TAG, "No fetch request available - aborting");
                    return null;
                }
            }
        };

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<T>> loader, List<T> items) {
        Log.d(TAG, "Done Loading...");
        if (items != null) {
            Log.d(TAG, "setting " + String.valueOf(items.size()) + " new items");

            clear();
            addItems(items);
            notifyDataSetChanged();
        } else {
            Log.d(TAG, "There are no items");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<T>> loader) {
        clear();
    }

    @Override
    public void cleanup() {
        if (mContentObserver != null) {
            mBridge.unregisterContentObserver(mContentObserver);
        }
    }
}
