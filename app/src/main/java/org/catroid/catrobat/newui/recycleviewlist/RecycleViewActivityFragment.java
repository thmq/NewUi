package org.catroid.catrobat.newui.recycleviewlist;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.ListItem;
import org.catroid.catrobat.newui.utils.Utils;

import java.util.List;

public class RecycleViewActivityFragment extends Fragment {

    private static String LOG_TAG = "RecycleViewFragment";
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycle_view, container, false);

        List<ListItem> items = Utils.getItemList();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, R.layout.list_item);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }
}
