package org.catroid.catrobat.newui.db.brigde;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.util.DataContract;

import java.util.List;

public class ProjectBridge extends DatabaseBridge<Project> {
    public ProjectBridge(Context context) {
        super(context);
    }

    @Override
    protected ContentValues serializeForDatabase(Project project) {
        ContentValues values = new ContentValues();

        values.put(DataContract.ProjectEntry.COLUMN_NAME,  project.getName());
        values.put(DataContract.ProjectEntry.COLUMN_INFO_TEXT,  project.getInfoText());
        values.put(DataContract.ProjectEntry.COLUMN_DESCRIPTION,  project.getDescription());
        values.put(DataContract.ProjectEntry.COLUMN_FAVORITE, project.getFavorite());

        return values;
    }

    @Override
    protected Project unserializeFromDatabaseCursor(Cursor cursor) {
        Project project = new Project();

        project.setId(cursor.getInt(cursor.getColumnIndex(DataContract.ProjectEntry._ID)));
        project.setName(cursor.getString(cursor.getColumnIndex(DataContract.ProjectEntry.COLUMN_NAME)));
        project.setInfoText(cursor.getString(cursor.getColumnIndex(DataContract.ProjectEntry.COLUMN_INFO_TEXT)));
        project.setDescription(cursor.getString(cursor.getColumnIndex(DataContract.ProjectEntry.COLUMN_DESCRIPTION)));
        project.setFavorite(cursor.getInt(cursor.getColumnIndex(DataContract.ProjectEntry.COLUMN_FAVORITE)) == 1);

        return project;
    }

    @Override
    protected String[] getProjection() {
        return DataContract.ProjectEntry.getFullProjection();
    }

    @Override
    protected Uri getCollectionUri() {
        return DataContract.ProjectEntry.PROJECT_URI;
    }

    @Override
    protected Uri getItemUri(long id) {
        return DataContract.ProjectEntry.getProjectUri(id);
    }

    @Override
    protected void beforeDestroy(Project item) {
        destroyScenes(item);
    }

    @Override
    protected void afterDestroy(Project item) {
    }

    private void destroyScenes(Project item) {
        SceneBridge sceneBridge = new SceneBridge(getContext());

        List<Scene> scenes = sceneBridge.findAll(new ChildCollectionFetchRequest(DataContract.SceneEntry.COLUMN_PROJECT_ID, item.getId()));

        for (Scene scene : scenes) {
            sceneBridge.delete(scene);
        }
    }

}
