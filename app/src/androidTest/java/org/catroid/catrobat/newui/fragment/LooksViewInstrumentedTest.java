package org.catroid.catrobat.newui.fragment;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.SpriteActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.catroid.catrobat.newui.fragment.Utils.addNewItemNamed;
import static org.catroid.catrobat.newui.fragment.Utils.checkItemNamedExists;
import static org.catroid.catrobat.newui.fragment.Utils.checkItemNamedNotExists;
import static org.catroid.catrobat.newui.fragment.Utils.selectItemNamed;

public class LooksViewInstrumentedTest {
    @Rule
    public ActivityTestRule<SpriteActivity> activityRule = new ActivityTestRule<>(SpriteActivity.class);

    @Before
    public void navigateToTab() {
        onView(ViewMatchers.withText(R.string.tab_name_looks)).perform(click());
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
    public void testCopyItems() {
        CommonTests.testCopyItems();
    }

    @Test
    public void testRemoveItems() {
        CommonTests.testRemoveItems();
    }

    @Test
    public void testEditItems() {
        CommonTests.testEditItems();
    }

}
