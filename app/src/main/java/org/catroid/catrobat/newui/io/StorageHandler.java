package org.catroid.catrobat.newui.io;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class StorageHandler {

    public static File copyFile(String srcPath) throws IOException {
        File file = new File(srcPath);
        if (!file.exists()) {
            throw new FileNotFoundException("File: " + srcPath + "does not exist.");
        }
        return new File(srcPath);
    }

    public static boolean deleteFile(String srcPath) throws FileNotFoundException {
        File file = new File(srcPath);
        if (!file.exists()) {
            throw new FileNotFoundException("File: " + srcPath + "does not exist.");
        }
        return file.delete();
    }
}
