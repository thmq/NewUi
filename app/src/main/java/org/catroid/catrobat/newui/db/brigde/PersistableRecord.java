package org.catroid.catrobat.newui.db.brigde;

public interface PersistableRecord {
    void setId(long id);
    long getId();

    void beforeDestroy();
    void afterDestroy();
}
