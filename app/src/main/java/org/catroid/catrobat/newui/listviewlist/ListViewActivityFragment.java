package org.catroid.catrobat.newui.listviewlist;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;

public class ListViewActivityFragment extends ListFragment {

    public ListViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }
}
