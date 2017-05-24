package org.catroid.catrobat.newui.data;

import android.media.MediaMetadataRetriever;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.io.PathInfoDirectory;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.io.Serializable;

public class SoundInfo extends ItemInfo implements Serializable, CopyPasteable {

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String fileName;
    private transient PathInfoFile mPathInfo;
    private String duration;

    public SoundInfo(String name, PathInfoFile pathInfo) {
        super(name);
        this.mPathInfo = pathInfo;

        //TODO what if the mPathInfo's relative path is not the filename alone?
        if (pathInfo != null) {
            fileName = pathInfo.getAbsolutePath();
            getDurationFromFile();
        }
    }

    public SoundInfo(SoundInfo srcSoundInfo) throws Exception {
        setName(srcSoundInfo.getName());
        duration = srcSoundInfo.getDuration();
        mPathInfo = StorageHandler.copyFile(srcSoundInfo.getPathInfo());
        //TODO what if the mPathInfo's relative path is not the filename alone?
        fileName = mPathInfo.getAbsolutePath();
    }

    public void initializeAfterDeserialize(PathInfoDirectory parent) {
        mPathInfo = new PathInfoFile(parent, fileName);
    }

    public PathInfoFile getPathInfo() {
        return mPathInfo;
    }

    public void setPathInfo(PathInfoFile pathInfo) {
        this.mPathInfo = pathInfo;
    }

    public void setAndCopyToPathInfo(PathInfoFile pathInfo) throws Exception {
        StorageHandler.copyFile(mPathInfo, pathInfo);
        setPathInfo(mPathInfo);
    }
    public int getThumbnailResource() {
        return R.drawable.ic_insert_photo_black_24dp;
    }

    public String getDuration() {
        return duration;
    }

    public void deleteFile() throws Exception {
        StorageHandler.deleteFile(mPathInfo);
    }

    private void getDurationFromFile() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mPathInfo.getAbsolutePath());
        duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    }


    @Override
    public SoundInfo clone() throws CloneNotSupportedException {
        SoundInfo clonedSoundInfo = (SoundInfo) super.clone();

        return clonedSoundInfo;
    }

    @Override
    public void prepareForClipboard() throws Exception {
        if (mPathInfo != null) {
            setAndCopyToPathInfo(PathInfoFile.getUniqueTmpFilePath(mPathInfo));
        }
    }

    @Override
    public void cleanupFromClipboard() throws Exception {
        StorageHandler.deleteFile(mPathInfo);
    }
}
