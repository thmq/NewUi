package org.catroid.catrobat.newui.io;

import java.io.File;

public class PathInfoDirectory extends PathInfo {

    public PathInfoDirectory(PathInfoDirectory parent, String directoryName) {
        super(parent, directoryName);
    }

    public PathInfoDirectory(String rootDirectoryPath) {
        this(null, rootDirectoryPath);
    }

    void checkPathSanity() throws IllegalArgumentException {
        if (parent != null) {
            // this is not a root directory, check relative path
            if (relativePath.contains(File.separator) || relativePath.contains("\\")) {
                throw new IllegalArgumentException("Parameter directoryName may not contain path separator characters.");
            }
        }

        File dir = new File(getAbsolutePath());
        // Note: Android API 26 will support java.nio.file package, which will make this check
        // way better.
        if (dir.exists() && !dir.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory.");
        }
    }
}
