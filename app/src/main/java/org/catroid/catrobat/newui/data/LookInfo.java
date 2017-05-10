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

public class LookInfo implements Serializable, CopyPasteable {

    private static final transient int THUMBNAIL_WIDTH = 80;
    private static final transient int THUMBNAIL_HEIGHT = 80;

    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String name;
    private String fileName;
    private transient PathInfoFile mPathInfo;
    private transient int width;
    private transient int height;
    private transient Bitmap mThumbnail;
    private RoundedBitmapDrawable mThumbnailDrawable;

    public LookInfo(String name, PathInfoFile pathInfo) {
        this.name = name;
        this.mPathInfo = pathInfo;
        //TODO what if the pathInfo's relative path is not the filename alone?
        fileName = pathInfo.getRelativePath();

        createThumbnail();
    }

    public void initializeAfterDeserialize(PathInfoDirectory parent) {
        mPathInfo = new PathInfoFile(parent, fileName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public void cleanup() throws Exception {
        StorageHandler.deleteFile(mPathInfo);
    }

    public Bitmap getBitmap() {
        String imagePath = mPathInfo.getAbsolutePath();

        if (!StorageHandler.fileExists(imagePath)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(imagePath, options);
    }

    public Drawable getRoundedDrawable() {
        if (mThumbnailDrawable == null) {
            Bitmap thumbnail = getThumbnail();

            mThumbnailDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), thumbnail);
            mThumbnailDrawable.setCircular(true);
        }

        return mThumbnailDrawable;
    }

    @Override
    public LookInfo clone() throws CloneNotSupportedException {
        LookInfo clonedLookInfo = (LookInfo) super.clone();
        mThumbnailDrawable = (RoundedBitmapDrawable) clonedLookInfo.mThumbnailDrawable.mutate();
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

    private void createThumbnail() {
        Bitmap bigImage = getBitmap();
        mThumbnail = ThumbnailUtils.extractThumbnail(bigImage, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
    }
}
