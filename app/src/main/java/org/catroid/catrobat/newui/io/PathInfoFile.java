package org.catroid.catrobat.newui.io;

import org.catroid.catrobat.newui.utils.Utils;

import java.io.File;

public class PathInfoFile extends PathInfo {

    public PathInfoFile(PathInfoDirectory parent, String fileName) {
        super(parent, fileName);
    }

    void checkPathSanity() throws IllegalArgumentException {
        File file = new File(getAbsolutePath());
        // Note: Android API 26 will support java.nio.file package, which will make this check
        // way better.
        if (file.exists() && !file.isFile()) {
            throw new IllegalArgumentException("Path is not a file.");
        }
    }

    public static PathInfoFile getUniqueTmpFilePath(PathInfoFile pathInfo) throws Exception {
        PathInfoDirectory tmpDir = Utils.getTmpDirectory();

        File uniqueFileName = StorageHandler.getUniqueFile(pathInfo.getBasename(), tmpDir.getAbsolutePath());

        return new PathInfoFile(tmpDir, uniqueFileName.getName());
    }


    public String getBasename() {
        int idx = relativePath.lastIndexOf(File.pathSeparator);

        if (idx != -1) {
            return relativePath.substring(idx);
        } else {
            return relativePath;
        }
    }
}
