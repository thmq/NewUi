package org.catroid.catrobat.newui.db.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.catroid.catrobat.newui.db.util.CatroidDBHelper;
import org.catroid.catrobat.newui.db.util.DataContract;

public class ProjectContentProvider extends ContentProvider {
    private CatroidDBHelper mDbHelper;

    private static final int PROJECTS = 1;
    private static final int PROJECT_WITH_ID = 2;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_PROJECT, PROJECTS);
        uriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_PROJECT + "/#", PROJECT_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();

        mDbHelper = new CatroidDBHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
            case PROJECTS:
                return db.query(DataContract.ProjectEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

            case PROJECT_WITH_ID:
                return db.query(DataContract.ProjectEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

            default:
                throw new UnsupportedOperationException();
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "application/Catroid.Project";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case PROJECTS:
                long id = db.insert(DataContract.ProjectEntry.TABLE_NAME, null, contentValues);

                if (id > 0) {
                    Uri projectUri = ContentUris.withAppendedId(DataContract.ProjectEntry.PROJECT_URI, id);

                    getContext().getContentResolver().notifyChange(projectUri, null);

                    return projectUri;
                } else {
                    throw new SQLException("Failed to insert row into: " + uri.toString());
                }
            default:
                throw new UnsupportedOperationException();
        }
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case PROJECT_WITH_ID:
                String idString = uri.getPathSegments().get(1);

                int deletedRecordsCount = db.delete(DataContract.ProjectEntry.TABLE_NAME, DataContract.ProjectEntry._ID + " = ? ", new String[]{idString});

                if (deletedRecordsCount != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return deletedRecordsCount;
            default:
                throw new UnsupportedOperationException("Cannot delete all projects");
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case PROJECT_WITH_ID:
                String idString = uri.getPathSegments().get(1);

                int updatedRecordsCount = db.update(DataContract.ProjectEntry.TABLE_NAME, contentValues,  DataContract.ProjectEntry._ID + " = ? ", new String[]{idString});

                if (updatedRecordsCount != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return updatedRecordsCount;
            default:
                throw new UnsupportedOperationException("Cannot update project: " + uri.toString());
        }
    }
}
