package org.catroid.catrobat.newui.copypaste;


public interface CopyPasteable extends Cloneable {
    void prepareForClipboard() throws Exception;
    void cleanupFromClipboard() throws Exception;

    CopyPasteable clone() throws CloneNotSupportedException;
}
