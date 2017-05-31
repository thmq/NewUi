package org.catroid.catrobat.newui.ui.comparator;

import org.catroid.catrobat.newui.data.Project;

import java.util.Comparator;

public class AlphabeticProjectComparator implements Comparator<Project> {

        @Override
        public int compare(Project project1, Project project2) {
            return project1.getInfoText().compareTo(project2.getInfoText());
        }
}
