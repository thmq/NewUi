package org.catroid.catrobat.newui.io;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class StorageHandler {

    public static final String TAG = StorageHandler.class.getSimpleName();

    public static final String ROOT = Environment.getExternalStorageDirectory().toString() + File.separator  + "NewUi";
    public static final String IMAGE_FOLDER = "images";
    public static final String SOUND_FOLDER = "sounds";

    public static final FileInfo rootDirectory = new FileInfo(null, ROOT);

    public static void exportBitmapToFile(Bitmap bitmap, File file) throws IOException {
        FileOutputStream os = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, os);
        os.flush();
        os.close();
    }

    public static FileInfo copyFileInfo(FileInfo srcFileInfo) throws Exception {
        String srcPath = srcFileInfo.getAbsolutePath();
        File dstFile = copyFile(srcPath);

        return new FileInfo(srcFileInfo.getParent(), dstFile.getName());
    }

    public static File copyFile(String srcPath) throws Exception {
        String dstPath = new File(srcPath).getParent();
        return copyFile(srcPath, dstPath);
    }

    public static File copyFile(String srcPath, String dstPath) throws Exception {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("File: " + srcPath + "does not exist.");
        }

        File dstFile = getUniqueFile(srcFile.getName(), dstPath);
        copyFile(srcFile, dstFile);

        return dstFile;
    }

    public static synchronized File getUniqueFile(String originalName, String dstDirectory) throws Exception {
        int extensionStartIndex = originalName.lastIndexOf(".");
        String extension = originalName.substring(extensionStartIndex);
        String fileName = originalName.substring(0, extensionStartIndex);

        int appendix = 0;

        while(appendix < Integer.MAX_VALUE) {
            String dstFileName = fileName + String.valueOf(appendix) + extension;
            File dstFile = new File(dstDirectory, dstFileName);
            if(!dstFile.exists()) {
                return dstFile;
            }
            appendix++;
        }

        throw new Exception("Could not find a unique file name.");
    }

    private static void copyFile(File srcFile, File dstFile) throws IOException {
        FileChannel ic = new FileInputStream(srcFile).getChannel();
        FileChannel oc = new FileOutputStream(dstFile).getChannel();

        try {
            ic.transferTo(0, ic.size(), oc);
        } finally {
            if (ic != null)
                ic.close();
            if (oc != null)
                oc.close();
        }
    }

    public static void deleteFile(String srcPath) throws IOException {
        File file = new File(srcPath);
        if (!file.exists()) {
            throw new FileNotFoundException("File: " + srcPath + "does not exist.");
        }
        if (!file.delete()) {
            throw new IOException("File: " + srcPath + "could not be deleted");
        }
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void setupDirectoryStructure() {
        mkDir(ROOT);
        mkDir(ROOT, IMAGE_FOLDER);
        mkDir(ROOT, SOUND_FOLDER);
    }

    private static void mkDir(String name) {
        File directory = new File(name);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                Log.d(TAG, "Directory NOT created! " + directory.getAbsolutePath());
            }
        }
    }

    private static void mkDir(String parentDirectory, String name) {
        File directory = new File(parentDirectory, name);

        if (!directory.exists()) {
            if (!directory.mkdir()) {
                Log.d(TAG, "Directory NOT created! " + directory.getAbsolutePath());
            }
        }
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
