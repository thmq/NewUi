package org.catroid.catrobat.newui.data;

import android.media.MediaMetadataRetriever;

import org.catroid.catrobat.newui.R;

import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.PathInfoDirectory;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.io.Serializable;

public class SoundInfo implements Serializable {

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String name;
    private String fileName;
    private transient PathInfoFile pathInfo;
    private String duration;

    public SoundInfo(String name, PathInfoFile pathInfo) {
        this.name = name;
        this.pathInfo = pathInfo;

        if (pathInfo != null) {
	        //TODO what if the pathInfo's relative path is not the filename alone?

	        fileName = pathInfo.getAbsolutePath();
            getDurationFromFile();
        }
    }

    public SoundInfo(SoundInfo srcSoundInfo) throws Exception {
        name = srcSoundInfo.getName();
        duration = srcSoundInfo.getDuration();
        pathInfo = StorageHandler.copyFile(srcSoundInfo.getPathInfo());
        //TODO what if the pathInfo's relative path is not the filename alone?
        fileName = pathInfo.getAbsolutePath();
    }

    public void initializeAfterDeserialize(PathInfoDirectory parent) {
        pathInfo = new PathInfoFile(parent, fileName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
