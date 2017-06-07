package org.catroid.catrobat.newui.db.brigde;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.util.DataContract;

import java.util.Date;
import java.util.List;

import static org.catroid.catrobat.newui.db.util.DataContract.ProjectEntry.*;


public class ProjectBridge extends DatabaseBridge<Project> {
    public ProjectBridge(Context context) {
        super(context);
    }

    @Override
    protected ContentValues serializeForDatabase(Project project) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME,  project.getName());
        values.put(COLUMN_INFO_TEXT,  project.getInfoText());
        values.put(COLUMN_DESCRIPTION,  project.getDescription());
        values.put(COLUMN_FAVORITE, project.getFavorite());
        values.put(COLUMN_LAST_ACCESS, serializeDate(project.getLastAccess()));

        return values;
    }

    @Override
    protected Project unserializeFromDatabaseCursor(Cursor cursor) {
        Project project = new Project();

        project.setId(unserializeId(cursor, _ID));
        project.setName(unserializeString(cursor, COLUMN_NAME));
        project.setInfoText(unserializeString(cursor, COLUMN_INFO_TEXT));
        project.setDescription(unserializeString(cursor, COLUMN_DESCRIPTION));
        project.setFavorite(unserializeBoolean(cursor, COLUMN_FAVORITE));

        project.setLastAccess(unserializeDate(cursor, COLUMN_LAST_ACCESS));

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
