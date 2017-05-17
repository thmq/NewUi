package org.catroid.catrobat.newui.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;

public class HintText {

    /**
     * @param message Message to show in HintText.
     * @param destItem HintText will be shown under this item.
     * @param context Current activity context.
     * @return Returns true if HintText was added.
     */
    public static boolean newInstance(String message, View destItem, Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hint_text, null, false);
        TextView textHint = (TextView) view.findViewById(R.id.text_hint);
        textHint.setText(message);

        View parent = (View) destItem.getParent();

        try {
            int index = ((ViewGroup) parent).indexOfChild(destItem);
            ((LinearLayoutCompat) parent).addView(view, index + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
