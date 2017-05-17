package org.catroid.catrobat.newui.db.brigde;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseBridge<T extends PersistableRecord> {
    private Context mContext;

    public DatabaseBridge(Context context) {
        mContext = context;
    }

    protected abstract ContentValues serializeForDatabase(T item);
    protected abstract T unserializeFromDatabaseCursor(Cursor values);

    protected abstract String[] getProjection();
    protected abstract Uri getCollectionUri();
    protected abstract Uri getItemUri(long id);

    public List<T> findAll() {
        return findAll(null, null, null);
    }

    public List<T> findAll(@Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = getFindAllCursor(selection, selectionArgs, sortOrder);

        return findAll(cursor);
    }

    public List<T> findAll(Cursor cursor) {
        List<T> items = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            T item = unserializeFromDatabaseCursor(cursor);
            items.add(item);
            cursor.moveToNext();
        }

        return items;
    }

    private Cursor getFindAllCursor(@Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return getContentResolver().query(getCollectionUri(), getProjection(), selection, selectionArgs, sortOrder);
    }

    public T find(long id) {
        return findBy(BaseColumns._ID, String.valueOf(id));
    }

    public T findBy(String column, String value) {
        String selection = column + " = ?";
        String[] selectionArgs = new String[]{value};

        return findBy(selection, selectionArgs);
    }

    public T findBy(String selection, String[] selectionArgs) {
        Cursor cursor = getFindCursor(selection, selectionArgs);

        return findBy(cursor);
    }

    public T findBy(Cursor cursor) {
        cursor.moveToFirst();
        T item;
        if (!cursor.isAfterLast()) {
            item = unserializeFromDatabaseCursor(cursor);
        } else {
            item = null;
        }

        return item;
    }

    private Cursor getFindCursor(String selection, String[] selectionArgs) {
        return getContentResolver().query(getCollectionUri(), getProjection(), selection, selectionArgs, null);
    }

    public long insert(T item) {
        Uri itemUri = getContentResolver().insert(getCollectionUri(), serializeForDatabase(item));

        long id = Long.valueOf(itemUri.getLastPathSegment());

        item.setId(id);

        return id;
    }

    public boolean update(T item) {
        Uri itemUri = getItemUri(item.getId());

        int updatedRecordCount = getContentResolver().update(itemUri, serializeForDatabase(item), null, null);

        return updatedRecordCount > 0;
    }

    public boolean delete(T item) {
        Uri itemUri = getItemUri(item.getId());

        int removedRecordsCount = getContentResolver().delete(itemUri, null, null);

        return removedRecordsCount > 0;
    }

    protected Context getContext() {
        return mContext;
    }

    private ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }

    public void registerContentObserver(ContentObserver observer) {
        getContentResolver().registerContentObserver(DataContract.ProjectEntry.PROJECT_URI, true, observer);
    }

    public void unregisterContentObserver(ContentObserver observer) {
        getContentResolver().unregisterContentObserver(observer);
    }
}
