package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.db.brigde.DatabaseBridge;
import org.catroid.catrobat.newui.db.brigde.PersistableRecord;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project implements PersistableRecord, CopyPasteable {

    private long mId;
    private String mName;
    private String infoText;
    private String description;
    private Bitmap thumbnail;
    private Boolean favorite;
    private Date mLastAccess;

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
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public void prepareForClipboard() throws Exception {

    }

    @Override
    public void cleanupFromClipboard() throws Exception {

    }

    @Override
    public CopyPasteable clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    @Override
    public void beforeDestroy() {

    }

    @Override
    public void afterDestroy() {
        // TODO: Remove any thumbnails!
    }

    public Date getLastAccess() {
        return mLastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        mLastAccess = lastAccess;
    }
}
