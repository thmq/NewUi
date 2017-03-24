package org.catroid.catrobat.newui.io;


import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class StorageHandler {

    public static final String ROOT = Environment.getExternalStorageDirectory().toString() + "NewUi";
    public static final String IMAGE_FOLDER = "images";
    public static final String SOUND_FOLDER = "sounds";

    public static final FileInfo rootDirectory = new FileInfo(null, ROOT);

    public static FileInfo createLookFileOnSD() {
        return new FileInfo(rootDirectory, "filepath.png");
    }

    public static FileInfo createSoundFileOnSD() {
        return new FileInfo(rootDirectory, "filepath.m4a");
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

        File dstFile = getUniqueFileName(srcPath, dstPath);
        copyFile(srcFile, dstFile);

        return dstFile;
    }

    private static synchronized File getUniqueFileName(String originalName, String dstDirectory) throws Exception {
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

    public static boolean deleteFile(String srcPath) throws FileNotFoundException {
        File file = new File(srcPath);
        if (!file.exists()) {
            throw new FileNotFoundException("File: " + srcPath + "does not exist.");
        }
        return file.delete();
    }

    public static void setupDirectoryStructure() {
        mkDir(ROOT);
        mkDir(ROOT, IMAGE_FOLDER);
        mkDir(ROOT, SOUND_FOLDER);
    }

    private static void mkDir(String name) {
        File directory = new File(name);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private static void mkDir(String name, String parentDirectory) {
        File directory = new File(parentDirectory, name);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
