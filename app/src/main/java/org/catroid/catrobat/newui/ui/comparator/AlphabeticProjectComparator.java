package org.catroid.catrobat.newui.ui.comparator;

import org.catroid.catrobat.newui.data.ProjectItem;

import java.util.Comparator;

public class AlphabeticProjectComparator implements Comparator<ProjectItem> {

        @Override
        public int compare(ProjectItem project1, ProjectItem project2) {
            return project1.getTitle().compareTo(project2.getTitle());
        }
}
