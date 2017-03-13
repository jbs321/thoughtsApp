package ca.webtosuccess.thoughts.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jacob on 3/11/2017.
 */

public class AsyncThoughts extends AsyncTask<String, Void, String[]> {

    public ArrayAdapter<String> tAdapter;
    private final Context mContext;
    public static final String URL_THOUGHTS = "http://10.0.2.2/thought_test";

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

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String thoughts = "";

        try {
            URL url = new URL(URL_THOUGHTS);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            thoughts = buffer.toString();

            try {
                return getDataFromJson(thoughts);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        tAdapter.clear();

        for (String thoughtStr : strings) {
            tAdapter.add(thoughtStr);
        }
    }

    private String[] getDataFromJson(String thoughtsJSON) throws JSONException {
        JSONObject user = new JSONObject(thoughtsJSON);
        user = user.getJSONObject("user");
        JSONArray thoughts = user.getJSONArray("thoughts");
        String[] thoughtArr = new String[thoughts.length()];

        for (int i = 0; i < thoughts.length(); i++) {
            thoughtArr[i] = (String)((JSONObject) thoughts.get(i)).get("description");
        }

        return thoughtArr;
    }
}
