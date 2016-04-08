package com.rafabertholdo.dotastats;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rafaelgb on 07/04/2016.
 */
class RequestTask extends AsyncTask<String, String, String> {

    public IApiAccessResponse delegate=null;
    @Override
    protected String doInBackground(String... uri) {

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(uri[0]);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder builder = new StringBuilder();

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                builder.append(current);
                //System.out.print(current);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(delegate!=null)
        {
            delegate.postResult(result);
        }
        else
        {
            Log.e("ApiAccess", "You have not assigned IApiAccessResponse delegate");
        }
    }
}