package org.catroid.catrobat.newui.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class StorageHandler {

    public static FileInfo createLookFileOnSD() {
        return new FileInfo(null, "filepath.png");
    }

    public static FileInfo createSoundFileOnSD() {
        return new FileInfo(null, "filepath.m4a");
    }

    public static File copyFile(String srcPath) throws IOException {
        String dstPath = new File(srcPath).getParent();
        return copyFile(srcPath, dstPath);
    }

    public static File copyFile(String srcPath, String dstPath) throws IOException {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("File: " + srcPath + "does not exist.");
        }

        File dstFile = new File(dstPath);
        copyFile(srcFile, dstFile);

        return dstFile;
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
}
