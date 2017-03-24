package org.catroid.catrobat.newui.data;


import android.graphics.Bitmap;

import org.catroid.catrobat.newui.io.FileInfo;

public class LookInfo {

    private static final int THUMBNAIL_WIDTH = 150;
    private static final int THUMBNAIL_HEIGHT = 150;

    private String name;
    private FileInfo fileInfo;
    private int width;
    private int height;
    private Bitmap thumbnail;

    public LookInfo(String name, FileInfo fileInfo) {
        this.name = name;
        this.fileInfo = fileInfo;
        createThumbnail();
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    private void createThumbnail() {
        //TODO: implement
    }

}
