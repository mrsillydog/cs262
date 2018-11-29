package isa3.cs262.calvin.edu.homework2;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String MONOPOLY_BASE_URL =  "https://calvincs262-monopoly.appspot.com/monopoly/v1/player"; // Base URI for the Monopoly API

    static String getPlayerInfo(String playerIDString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String playerJSONString;

        try {
            Uri builtURI;
            //Build up your query URI, limiting results to 10 items and printed books
            if(playerIDString.equals("")) {
                builtURI = Uri.parse(MONOPOLY_BASE_URL.concat("s"));
            } else {
                builtURI = Uri.parse(MONOPOLY_BASE_URL.concat("/").concat(playerIDString));
            }


            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
   /* Since it's JSON, adding a newline isn't necessary (it won't affect
      parsing) but it does make debugging a *lot* easier if you print out the
      completed buffer for debugging. */
                buffer.append(line).append("\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            playerJSONString = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, playerJSONString);
        return playerJSONString;
    }
}

