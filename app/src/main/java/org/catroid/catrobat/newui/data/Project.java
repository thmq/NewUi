package org.catroid.catrobat.newui.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

import org.catroid.catrobat.newui.db.brigde.PersistableRecord;
import org.catroid.catrobat.newui.db.util.DataContract;

import java.util.ArrayList;
import java.util.List;

public class Project implements PersistableRecord {

    private long mId;
    private String infoText;
    private String description;
    private Bitmap thumbnail;
    private Boolean favorite;

    private List<Scene> mScenes = new ArrayList<>();

    public Project() {
        // TODO: Maybe use complete signature constructors only - to prevent user errors
    }

    public Project(Bitmap thumbnail, String infoText) {
        this.thumbnail = thumbnail;
        this.infoText = infoText;
        this.favorite = false;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setScenes(List<Scene> scenes) {
        mScenes = scenes;
    }

    public List<Scene> getScenes() {
        return mScenes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getName() {
        return infoText;
    }
}
