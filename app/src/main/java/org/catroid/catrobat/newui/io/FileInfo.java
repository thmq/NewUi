
package org.catroid.catrobat.newui.io;

import java.io.File;

public class FileInfo {

    private FileInfo parent;
    private String relativePath;

    public FileInfo(FileInfo parent, String relativePath) {
        this.parent = parent;
        this.relativePath = relativePath;
    }

    public FileInfo getParent() {
        return parent;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getAbsolutePath() {
        if (parent == null) {
            return relativePath;
        } else {
            return getParent().getAbsolutePath() + File.pathSeparator + relativePath;
        }
    }
}