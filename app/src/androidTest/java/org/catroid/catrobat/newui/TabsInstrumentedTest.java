package org.catroid.catrobat.newui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class TabsInstrumentedTest {
//    @Rule
//    public ActivityTestRule<TabsViewActivity> activityRule = new ActivityTestRule<>(TabsViewActivity.class);
//
//    private static String SOUNDS_TAB_TEXT = "Sounds";
//    private static String LOOKS_TAB_TEXT = "Looks";
//
//    @Test
//    public void testTabsExist() {
//        onView(withText(SOUNDS_TAB_TEXT)).perform(click());
//        onView(withText(LOOKS_TAB_TEXT)).perform(click());
//    }
//
//    @Test
//    public void testContentOfSoundsTab() {
//        onView(withText(SOUNDS_TAB_TEXT)).perform(click());
//        onView(withId(R.id.tab_contents)).check(matches(withText("Sound 1")));
//    }
//
//    @Test
//    public void testContentOfLooksTab() {
//        onView(withText(LOOKS_TAB_TEXT)).perform(click());
//        onView(withId(R.id.tab_contents)).check(matches(withText("Look 1")));
//    }
}
