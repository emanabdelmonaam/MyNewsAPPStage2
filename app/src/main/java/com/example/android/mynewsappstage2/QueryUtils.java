package com.example.android.mynewsappstage2;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }

    /**
     * Returns new URL object from the given string URL.
     */
    public static ArrayList<News> fetchMyNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }

        // Return the list
        return (ArrayList<News>) extractFeatureFromJson(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {

            }
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<News> extractFeatureFromJson(String myNewsJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(myNewsJSON)) {
            return null;
        }

        // Create an empty ArrayList
        ArrayList<News> myNewsArray = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(myNewsJSON);
            JSONObject responsJsonObject = baseJsonResponse.getJSONObject("response");
            JSONArray firstArrayJson = responsJsonObject.getJSONArray("results");

            //JSONObject firstFeature = newsArrayJson.getJSONObject(0);

            for (int i = 0; i < firstArrayJson.length(); i++) {

                JSONObject currentJson = firstArrayJson.getJSONObject(i);

                String title = currentJson.getString("webTitle");
                String type = currentJson.getString("type");
                String sectionName = currentJson.getString("sectionName");

                //String publishedTime = currentJson.getString("webPublicationDate");
                // Extract the value for the key called "webPublicationDate"
                String publishedTime = currentJson.getString("webPublicationDate");

                //Format publication date
                Date finalDate = null;
                try {
                    finalDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")).parse(publishedTime);
                } catch (Exception e) {
                    // If an error is thrown when executing the above statement in the "try" block,
                    // catch the exception here, so the app doesn't crash. Print a log message
                    // with the message from the exception.
                }



                String url = currentJson.getString("webUrl");
                String authorName=currentJson.getString("pillarName");
               /** JSONArray tagsArrayJson = currentJson.getJSONArray("tags");
                String authorName = "";
                if (tagsArrayJson.length() != 0) {
                    JSONObject currentTagsArray = tagsArrayJson.getJSONObject(0);
                    authorName = currentTagsArray.getString("webTitle");
                } else {
                    authorName = "No Author ..";
                }*/

                News myItemNews = new News(title, type, sectionName, finalDate, authorName, url);
                myNewsArray.add(myItemNews);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return to the list of news
        return myNewsArray;
    }
}