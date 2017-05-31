package org.catroid.catrobat.newui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragment;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;
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

    class TestItem implements CopyPasteable {
        String mName;

        TestItem() {
            mName = "unnamed";
        }

        TestItem(String name) {
            mName = name;
        }

        @Override
        public void prepareForClipboard() throws Exception {

        }

        @Override
        public void cleanupFromClipboard() throws Exception {

        }

        @Override
        public CopyPasteable clone() throws CloneNotSupportedException {
            return null;
        }
    }

    public class TestFragment extends BaseRecyclerListFragment<TestItem> {
        @Override
        public RecyclerViewAdapter<TestItem> createAdapter() {
            return new TestAdapter(new ArrayList<TestItem>(), 0);
        }

        @Override
        protected String getItemName(TestItem item) {
            return null;
        }

        @Override
        protected Clipboard.ItemType getItemType() {
            return null;
        }

        @Override
        protected TestItem copyItem(TestItem item) throws Exception {
            return null;
        }

        @Override
        protected void cleanupItem(TestItem item) throws Exception {
        }

        @Override
        protected void renameItem(TestItem item, String itemName) {

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
            public RecyclerViewHolder createViewHolder(View view) {
                return null;
            }

            @Override
            public void bindDataToViewHolder(TestItem item, RecyclerViewHolder holder, boolean isSelected) {

            }
        }
    }

}
