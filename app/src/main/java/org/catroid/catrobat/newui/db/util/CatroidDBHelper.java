package org.catroid.catrobat.newui.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.catroid.catrobat.newui.db.util.DataContract.ProjectEntry;

public class CatroidDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "catroid.sqlite3";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DB";

    public CatroidDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createProjectsTableSQL = createProjectsTable();

        Log.d(TAG, "Creating Projects... ");
        Log.d(TAG, createProjectsTableSQL);
        sqLiteDatabase.execSQL(createProjectsTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionFrom, int versionTo) {
        Log.d(TAG, "Upgrading ... ");
        sqLiteDatabase.execSQL(SQLHelper.dropTableIfExists(ProjectEntry.TABLE_NAME));

        onCreate(sqLiteDatabase);
    }

    private String createProjectsTable() {
        return SQLHelper.createTableDefinition(ProjectEntry.TABLE_NAME, new String[]{
                SQLHelper.idColumnDefinition(ProjectEntry._ID),
                SQLHelper.stringColumnDefinition(ProjectEntry.COLUMN_NAME),
                SQLHelper.stringColumnDefinition(ProjectEntry.COLUMN_INFO_TEXT),
                SQLHelper.stringColumnDefinition(ProjectEntry.COLUMN_DESCRIPTION),
                SQLHelper.booleanColumnDefinition(ProjectEntry.COLUMN_FAVORITE)
        });
    }

}
