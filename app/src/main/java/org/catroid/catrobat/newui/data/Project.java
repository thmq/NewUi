package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;

import java.util.ArrayList;
import java.util.List;

public class Project{

    private static final transient int THUMBNAIL_SIZE = 200;
    private String infoText;
    private Bitmap thumbnail;

    private List<Scene> mScenes = new ArrayList<>();

    public Project(Bitmap thumbnail, String infoText) {
        this.thumbnail = thumbnail;
        this.infoText = infoText;
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

}
