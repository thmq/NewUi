package org.catroid.catrobat.newui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.InputType;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.catroid.catrobat.newui.ui.SpriteActivity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecyclerviewRenameTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("org.catroid.catrobat.newui", appContext.getPackageName());
    }
    @Rule
    public ActivityTestRule<SpriteActivity> activityRule = new ActivityTestRule<>(SpriteActivity.class);

    @Test
    public void testCorrectRenaming()
    {
        onView(withText(R.string.title_activity_recycle_view)).perform(click());
        onView(withText("Item3")).check(matches(isDisplayed()));

        onView(withText("Item3")).perform(longClick());
        onView(withId(R.id.btnEdit)).perform(click());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Test"));
        onView(withText(R.string.rename_button)).perform(click());
        onView(withText("Test")).check(matches(isDisplayed()));
    }
    @Test
    public void testIncorrectRenaming()
    {
        onView(withText(R.string.title_activity_recycle_view)).perform(click());
        onView(withText("Item3")).check(matches(isDisplayed()));

        onView(withText("Item3")).perform(longClick());
        onView(withId(R.id.btnEdit)).perform(click());
        onView(withInputType(InputType.TYPE_CLASS_TEXT)).perform(typeText("Item3"));
        onView(withText(R.string.rename_button)).perform(click());
        onView(withText(R.string.dialog_rename_item)).check(matches(isDisplayed()));
    }
}
