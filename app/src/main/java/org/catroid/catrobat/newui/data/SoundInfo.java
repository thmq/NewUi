package org.catroid.catrobat.newui.data;

import android.media.MediaMetadataRetriever;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.io.FileInfo;
import org.catroid.catrobat.newui.io.StorageHandler;

public class SoundInfo {

    private String name;
    private FileInfo fileInfo;
    private String duration;

    public SoundInfo(String name, FileInfo fileInfo) {
        this.name = name;
        this.fileInfo = fileInfo;
        getDurationFromFile();
    }

    public SoundInfo(SoundInfo srcSoundInfo) throws Exception {
        name = srcSoundInfo.getName();
        duration = srcSoundInfo.getDuration();
        fileInfo = StorageHandler.copyFile(srcSoundInfo.getFileInfo());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public int getThumbnailResource() {
        return R.drawable.ic_insert_photo_black_24dp;
    }

    public String getDuration() {
        return duration;
    }
    
    public void deleteFile() throws Exception {
        StorageHandler.deleteFile(fileInfo);
    }

    private void getDurationFromFile() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(fileInfo.getAbsolutePath());
        duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    }
}
