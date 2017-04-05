package org.catroid.catrobat.newui;

import org.catroid.catrobat.newui.ui.fragment.LookListFragment;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

public class LookListFragmentTest {
    LookListFragment subject;
    @Before
    public void setup() {
        subject = createLookListFragment();
    }


    @Test
    public void testGetTabNameResource() {
        assertEquals(R.string.tab_name_looks, subject.getTabNameResource());
    }

    @Test
    public void testCreateAdapter() {
        assertNotNull(subject.createAdapter());
    }

    private LookListFragment createLookListFragment() {
        LookListFragment fragment = new LookListFragment();

        return fragment;
    }



}
