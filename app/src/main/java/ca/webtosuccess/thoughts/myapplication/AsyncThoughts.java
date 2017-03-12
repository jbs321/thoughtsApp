package ca.webtosuccess.thoughts.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

/**
 * Created by jacob on 3/11/2017.
 */

public class AsyncThoughts extends AsyncTask<String, Void, String[]> {

    public ArrayAdapter<String> tAdapter;
    private final Context mContext;

    public AsyncThoughts(ArrayAdapter<String> tAdapter, Context mContext) {
        this.tAdapter = tAdapter;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {

        String[] ret = {"aaaaaaaa", "bbbbbbbbb", "ccccccc", "ddddddddddd"};
        return ret;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        tAdapter.clear();

        for(String thoughtStr : strings) {
            tAdapter.add(thoughtStr);
        }
    }
}
