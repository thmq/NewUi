package org.catroid.catrobat.newui.ui.comparator;

import org.catroid.catrobat.newui.data.ProjectItem;

import java.util.Comparator;

public class RecentProjectComparator implements Comparator<ProjectItem> {

    @Override
    public int compare(ProjectItem project1, ProjectItem project2) {
        return (-1) * project1.getLastAccess().compareTo(project2.getLastAccess());
    }
}
