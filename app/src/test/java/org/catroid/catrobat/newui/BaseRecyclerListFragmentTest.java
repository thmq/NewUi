package org.catroid.catrobat.newui;

import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragment;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class BaseRecyclerListFragmentTest {

    @Test
    public void testValidItemNames() {
        TestFragment fragment = createFragment();

        assertFalse(fragment.isNameValid(null));
        assertFalse(fragment.isNameValid(""));

        assertTrue(fragment.isNameValid("Name"));
    }

    private TestFragment createFragment() {
        return new TestFragment();
    }

    class TestItem {
        String mName;

        TestItem() {
            mName = "unnamed";
        }

        TestItem(String name) {
            mName = name;
        }
    }

    private class TestFragment extends BaseRecyclerListFragment<TestItem> {
        @Override
        public int getTabNameResource() {
            return 0;
        }

        @Override
        public RecyclerViewAdapter<TestItem> createAdapter() {
            return new TestAdapter(new ArrayList<TestItem>(), 0);
        }

        @Override
        protected TestItem copyItem(TestItem item) throws Exception {
            return null;
        }

        @Override
        protected void cleanupItem(TestItem item) throws Exception {
        }

        @Override
        protected TestItem createNewItem(String itemName) {
            return new TestItem(itemName);
        }

        class TestAdapter extends RecyclerViewAdapter<TestItem> {
            public TestAdapter(List<TestItem> listItems, int itemLayout) {
                super(listItems, itemLayout);
            }

            @Override
            public void bindDataToViewHolder(TestItem item, ViewHolder holder, boolean isSelected) {

            }
        }
    }

}
