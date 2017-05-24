package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;

import java.util.Date;

public class ProjectItem {

    private String title;
    private String description;
    private Bitmap thumbnail;
    private Boolean favorite;
    private Date lastAccess;

    public ProjectItem(Bitmap thumbnail, String title) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.favorite = false;
        this.lastAccess = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
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
