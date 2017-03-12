package ca.webtosuccess.thoughts.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ThoughtsFragment extends Fragment {

    public ArrayAdapter<String> tAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.view_thoughts) {
            updateThoughts();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_thoughts, R.id.list_thoughts_textview, new ArrayList<String>());
        View rootView = inflater.inflate(R.layout.fragment_thoughts, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.listview_thoughts);
        lv.setAdapter(tAdapter);
        return rootView;
    }

    public void updateThoughts() {
        AsyncThoughts at = new AsyncThoughts(tAdapter, getContext());
        at.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateThoughts();
    }
}
