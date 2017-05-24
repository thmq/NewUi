package org.catroid.catrobat.newui.io;

import org.catroid.catrobat.newui.utils.Utils;

import java.io.File;

public abstract class PathInfo {

    protected PathInfoDirectory parent;
    protected String relativePath;

    protected PathInfo(PathInfoDirectory parent, String relativePath) {
        this.parent = parent;
        this.relativePath = relativePath;
        checkPathSanity();
    }

    public PathInfoDirectory getParent() {
        return parent;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getAbsolutePath() {
        if (parent == null) {
            return relativePath;
        } else {
            return getParent().getAbsolutePath() + File.separator + relativePath;
        }
    }

    abstract void checkPathSanity() throws IllegalArgumentException;
}
