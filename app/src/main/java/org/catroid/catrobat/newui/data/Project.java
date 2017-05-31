package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project{

    private String infoText;
    private String description;
    private Bitmap thumbnail;
    private Boolean favorite;
    private Date lastAccess;

    private List<Scene> mScenes = new ArrayList<>();

    public Project(Bitmap thumbnail, String infoText) {
        this.thumbnail = thumbnail;
        this.infoText = infoText;
        this.favorite = false;
        this.lastAccess = new Date();
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

    public Date getLastAccess() { return lastAccess; }

    public void setLastAccess(Date lastAccess) { this.lastAccess = lastAccess; }
}
