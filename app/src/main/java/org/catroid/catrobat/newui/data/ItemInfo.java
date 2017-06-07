package org.catroid.catrobat.newui.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

public abstract class ItemInfo {
    private static final transient int THUMBNAIL_WIDTH = 80;
    private static final transient int THUMBNAIL_HEIGHT = 80;

    private String name;
    private transient Bitmap mThumbnail;
    private RoundedBitmapDrawable mThumbnailDrawable;

    public ItemInfo(String name) {
        this.name = name;
    }

    public ItemInfo(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getRoundedDrawable() {
        if (mThumbnailDrawable == null) {
            Bitmap thumbnail = getThumbnail();

            mThumbnailDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(),
                    thumbnail);

            mThumbnailDrawable.setCircular(true);
        }
        return mThumbnailDrawable;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Bitmap mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public RoundedBitmapDrawable getThumbnailDrawable() {
        return mThumbnailDrawable;
    }

    public void setThumbnailDrawable(RoundedBitmapDrawable mThumbnailDrawable) {
        this.mThumbnailDrawable = mThumbnailDrawable;
    }

    protected void createThumbnail() {
        Bitmap bigImage = getBitmap();
        setThumbnail(ThumbnailUtils.extractThumbnail(bigImage, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT));
    }

    public abstract Bitmap getBitmap();
}
