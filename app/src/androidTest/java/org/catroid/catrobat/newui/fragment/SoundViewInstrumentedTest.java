package org.catroid.catrobat.newui.fragment;

import android.support.test.rule.ActivityTestRule;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.activity.SpriteActivity;
import org.catroid.catrobat.newui.utils.BaseActivityInstrumentationRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SoundViewInstrumentedTest {
    @Rule
    public ActivityTestRule<SpriteActivity> activityRule =
            new BaseActivityInstrumentationRule<>(SpriteActivity.class);

    @Before
    public void navigateToTab() {
        Utils.navigateToTab(R.string.tab_name_sounds);
    }

    @Test
    public void testAddNewItem() {
        CommonTests.testAddNewItem();
    }

    @Test
    public void testAddMultipleItems() {
        CommonTests.testAddMultipleItems();
    }

    @Test
    public void testCopyPasteItems() {
        CommonTests.testCopyPasteItems();
    }

    @Test
    public void testRemoveItems() {
        CommonTests.testRemoveItems();
    }

    @Test
    public void testEditItems() {
        CommonTests.testEditItems();
    }

    @Test
    public void testIncorrectRenaming() {
        CommonTests.testIncorrectRenaming();
    }
}
