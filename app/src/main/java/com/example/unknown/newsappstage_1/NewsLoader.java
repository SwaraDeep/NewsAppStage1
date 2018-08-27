package com.example.unknown.newsappstage_1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;

    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return QueryUtils.fetchNews(mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
