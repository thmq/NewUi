package org.catroid.catrobat.newui.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.io.PathInfoDirectory;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;

import java.io.Serializable;

public class LookInfo extends ItemInfo implements Serializable, CopyPasteable {

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String fileName;
    private transient PathInfoFile mPathInfo;
    private transient int width;
    private transient int height;

    public LookInfo(String name, PathInfoFile pathInfo) {
        super(name);
        this.mPathInfo = pathInfo;
        //TODO what if the pathInfo's relative path is not the filename alone?
        fileName = pathInfo.getRelativePath();

        createThumbnail();
    }

    public void initializeAfterDeserialize(PathInfoDirectory parent) {
        mPathInfo = new PathInfoFile(parent, fileName);
    }

    public PathInfoFile getPathInfo() {
        return mPathInfo;
    }

    public void setPathInfo(PathInfoFile pathInfo) {
        mPathInfo = pathInfo;
    }

    public void setAndCopyToPathInfo(PathInfoFile pathInfo) throws Exception {
        StorageHandler.copyFile(mPathInfo, pathInfo);
        setPathInfo(pathInfo);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void cleanup() throws Exception {
        StorageHandler.deleteFile(mPathInfo);
    }

    @Override
    public Bitmap getBitmap() {
        String imagePath = mPathInfo.getAbsolutePath();

        if (!StorageHandler.fileExists(imagePath)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(imagePath, options);
    }

    @Override
    public LookInfo clone() throws CloneNotSupportedException {
        LookInfo clonedLookInfo = (LookInfo) super.clone();
        setThumbnailDrawable((RoundedBitmapDrawable) clonedLookInfo.getThumbnailDrawable().mutate());
        return clonedLookInfo;
    }

    @Override
    public void prepareForClipboard() throws Exception {
        setAndCopyToPathInfo(PathInfoFile.getUniqueTmpFilePath(mPathInfo));

        createThumbnail();
    }

    @Override
    public void cleanupFromClipboard() throws Exception {
        cleanup();
    }


}
