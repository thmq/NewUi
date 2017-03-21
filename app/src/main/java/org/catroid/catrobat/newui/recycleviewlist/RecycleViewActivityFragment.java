package org.catroid.catrobat.newui.recycleviewlist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;

public class RecycleViewActivityFragment extends Fragment {

    public RecycleViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycle_view, container, false);
    }
}
