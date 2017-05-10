package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;

public class ProjectItem {

    private String infoText;
    private String description;
    private Bitmap thumbnail;
    private Boolean favorite;

    public ProjectItem(Bitmap thumbnail, String infoText) {
        this.thumbnail = thumbnail;
        this.infoText = infoText;
        this.favorite = false;
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
}
