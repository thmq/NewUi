package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import org.catroid.catrobat.newui.io.FileInfo;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.io.Serializable;

public class LookInfo implements Serializable {

    private static final transient int THUMBNAIL_WIDTH = 150;
    private static final transient int THUMBNAIL_HEIGHT = 150;

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String name;
    private String fileName;
    private transient FileInfo fileInfo;
    private transient int width;
    private transient int height;
    private transient Bitmap thumbnail;

    public LookInfo(String name, FileInfo fileInfo) {
        this.name = name;
        this.fileInfo = fileInfo;
        //TODO what if the fileInfo's relative path is not the filename alone?
        fileName = fileInfo.getRelativePath();
        createThumbnail();
    }

    public void initializeAfterDeserialize(FileInfo parent) {
        fileInfo = new FileInfo(parent, fileName);
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

    public void cleanup() throws Exception {
        StorageHandler.deleteFile(fileInfo);
    }

    public Bitmap getBitmap() {
        String imagePath = fileInfo.getAbsolutePath();

        if (!StorageHandler.fileExists(imagePath)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(imagePath, options);
    }

    private void createThumbnail() {
        Bitmap bigImage = getBitmap();
        thumbnail = ThumbnailUtils.extractThumbnail(bigImage, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

}
