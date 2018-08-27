package com.example.unknown.newsappstage_1;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private NewsAdapter mAdapter;
    private static final int News_Loader_Id = 1;
    private static final String GUARDIANS_NEWS_URL = "https://content.guardianapis.com/search?api-key=5c1b0874-ccaf-41c5-92dc-872d56c90cf1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        ListView list = (ListView) findViewById(R.id.list);

        list.setEmptyView(emptyTextView);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        list.setAdapter(mAdapter);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(News_Loader_Id, null, this);
        } else {
            emptyTextView.setText("Not connected to network");
            pb.setVisibility(View.GONE);
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentNews = mAdapter.getItem(i);
                Uri newsUri = Uri.parse(currentNews.getmUrl());

                Intent openLink = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(openLink);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, GUARDIANS_NEWS_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        mAdapter.clear();
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }

        TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
        emptyTextView.setText("no news found");
        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {
        mAdapter.clear();
    }


}
