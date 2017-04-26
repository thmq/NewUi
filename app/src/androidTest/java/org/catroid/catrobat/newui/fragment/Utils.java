package org.catroid.catrobat.newui.fragment;

import android.support.test.espresso.matcher.ViewMatchers;

import org.catroid.catrobat.newui.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class Utils {

    static void addNewItemNamed(String name) {
        onView(ViewMatchers.withId(R.id.fab)).perform(click());
        onView(withId(R.id.input)).perform(typeText(name));
        onView(withText(R.string.dialog_create_item_primary_action)).perform(click());
    }

    static void checkItemNamedExists(String name) {
        onView(withText(name)).check(matches(isDisplayed()));
    }

    static void checkItemNamedNotExists(String name) {
        onView(withText(name)).check(doesNotExist());
    }

    static void selectItemNamed(String name) {
        onView(withText(name)).perform(longClick());
    }

    static void navigateToTab(int resourceName) {
        onView(ViewMatchers.withText(resourceName)).perform(click());
    }
}
