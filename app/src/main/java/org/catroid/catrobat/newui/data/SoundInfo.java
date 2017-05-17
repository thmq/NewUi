package org.catroid.catrobat.newui.data;

import android.media.MediaMetadataRetriever;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.io.PathInfoDirectory;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.io.Serializable;

public class SoundInfo extends ItemInfo implements Serializable {

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String fileName;
    private transient PathInfoFile pathInfo;
    private String duration;

    public SoundInfo(String name, PathInfoFile pathInfo) {
        super(name);
        this.pathInfo = pathInfo;

        //TODO what if the pathInfo's relative path is not the filename alone?
        if (pathInfo != null) {
            fileName = pathInfo.getAbsolutePath();
            getDurationFromFile();
        }
    }

    public SoundInfo(SoundInfo srcSoundInfo) throws Exception {
        setName(srcSoundInfo.getName());
        duration = srcSoundInfo.getDuration();
        pathInfo = StorageHandler.copyFile(srcSoundInfo.getPathInfo());
        //TODO what if the pathInfo's relative path is not the filename alone?
        fileName = pathInfo.getAbsolutePath();
    }

    public void initializeAfterDeserialize(PathInfoDirectory parent) {
        pathInfo = new PathInfoFile(parent, fileName);
    }

    public PathInfoFile getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(PathInfoFile pathInfo) {
        this.pathInfo = pathInfo;
    }

    public int getThumbnailResource() {
        return R.drawable.ic_insert_photo_black_24dp;
    }

    public String getDuration() {
        return duration;
    }

    public void deleteFile() throws Exception {
        StorageHandler.deleteFile(pathInfo);
    }

    private void getDurationFromFile() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(pathInfo.getAbsolutePath());
        duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    }
}
