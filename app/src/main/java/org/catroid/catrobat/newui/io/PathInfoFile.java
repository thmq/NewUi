package org.catroid.catrobat.newui.io;

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
}
