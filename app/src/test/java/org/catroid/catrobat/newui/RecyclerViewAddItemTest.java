package org.catroid.catrobat.newui;

import android.widget.EditText;
import android.widget.ImageView;

import org.catroid.catrobat.newui.ui.AddItemActivity;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class RecyclerViewAddItemTest {
    private int itemCount = 0;


    @Test
    public void testNameFieldPreconditions() {
        AddItemActivity activity = createAddItemActivity();
        EditText itemName = (EditText) activity.findViewById(R.id.addItemNameTxt);
        assertNotNull(itemName);
        assertEquals("", itemName.getText());
    }

    @Test
    public void testImageViewPreconditions() {
        AddItemActivity activity = createAddItemActivity();
        ImageView addImage = (ImageView) activity.findViewById(R.id.addItemImage);
        assertNotNull(addImage);
        assertNotNull(addImage.getResources());
    }

    @Test
    public void testNameFieldOnWrite() {
        AddItemActivity activity = createAddItemActivity();
        EditText itemName = (EditText) activity.findViewById(R.id.addItemNameTxt);
        itemName.setText("new item 1");
        assertEquals("new item 1", itemName.getText());
    }



    private AddItemActivity createAddItemActivity() {
        return new AddItemActivity();
    }

    private ListItem createListItem() {
        itemCount++;
        return new ListItem("test item " + itemCount);
    }

    private class ListItem {
        private final String mName;

        public ListItem(String name) {
            mName = name;
        }
    }
}
