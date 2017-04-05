package org.catroid.catrobat.newui.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
        thumbnail = ThumbnailUtils.extractThumbnail(bigImage, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
