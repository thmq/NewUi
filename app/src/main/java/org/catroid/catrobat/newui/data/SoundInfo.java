package org.catroid.catrobat.newui.data;


import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.io.FileInfo;

public class SoundInfo {

    private String name;
    private FileInfo fileInfo;
    private String duration;

    public SoundInfo(String name, FileInfo fileInfo) {
        this.name = name;
        this.fileInfo = fileInfo;
        getDurationFromFile();
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

    private void getDurationFromFile() {
        //TODO: implement
    }
}
