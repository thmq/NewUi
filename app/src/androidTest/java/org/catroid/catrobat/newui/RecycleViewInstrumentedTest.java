package org.catroid.catrobat.newui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.catroid.catrobat.newui.data.ListItem;
import org.catroid.catrobat.newui.recycleviewlist.RecycleViewActivity;
import org.catroid.catrobat.newui.utils.Utils;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.catroid.catrobat.newui.Utils.atPosition;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by matthee on 22.03.17.
 */
@RunWith(AndroidJUnit4.class)

public class RecycleViewInstrumentedTest {
    @Rule
    public ActivityTestRule<RecycleViewActivity> activityRule = new ActivityTestRule<>(RecycleViewActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("org.catroid.catrobat.newui", appContext.getPackageName());
    }

    @Test
    public void testLongClickOnListItems() {
        List<ListItem> items = Utils.getItemList();

        for (int i = 0; i < 10; i++) {
            ListItem item = items.get(i);

            onView(withId(R.id.fragment)).perform(
                    RecyclerViewActions.actionOnItem(
                            hasDescendant(withText(item.getName()))
                            , longClick())
            );

        }
    }

}
