package com.example.unknown.newsappstage_1;

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
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final int ReadTimeOut = 10000;
    private static final int ConnectTimeOut = 15000;

    private QueryUtils() {
    }

    public static List<News> fetchNews(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<News> News = extractFeatureFromJson(jsonResponse);
        return News;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(ReadTimeOut);
            urlConnection.setConnectTimeout(ConnectTimeOut);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
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


    private static List<News> extractFeatureFromJson(String NewsJSON) {
        if (TextUtils.isEmpty(NewsJSON)) {
            return null;
        }
        List<News> news = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(NewsJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject currentNews = results.getJSONObject(i);

                String type = currentNews.optString("type");

                String title = currentNews.optString("sectionName");

                String content = currentNews.optString("webTitle");

                String url = currentNews.optString("webUrl");

                String date = currentNews.optString("webPublicationDate");

                String author;
                try {
                    JSONArray tags = currentNews.getJSONArray("tags");
                    JSONObject jo = tags.getJSONObject(0);
                    author = jo.getString("webTitle");
                } catch (Exception e) {
                    author = "unknown";
                    Log.v("Excptn at authors tag", "No author available");
                }

                News n = new News(title, type, content, url, date, "by ".concat(author));

                news.add(n);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the News from JSON results", e);
        }

        return news;
    }

}
