package org.catroid.catrobat.newui.ui.comparator;

import org.catroid.catrobat.newui.data.Project;

import java.util.Comparator;

public class RecentProjectComparator implements Comparator<Project> {

    @Override
    public int compare(Project project1, Project project2) {
        return (-1) * project1.getLastAccess().compareTo(project2.getLastAccess());
    }
}
