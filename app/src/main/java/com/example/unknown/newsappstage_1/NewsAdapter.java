package com.example.unknown.newsappstage_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemview = convertView;
        if (listitemview == null) {
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        News currentNews = getItem(position);
        TextView mainTextView = (TextView) listitemview.findViewById(R.id.main_text);
        TextView titleTextView = (TextView) listitemview.findViewById(R.id.title_text);
        TextView typeOfNews = (TextView) listitemview.findViewById(R.id.type);

        mainTextView.setText(currentNews.getmContent());
        titleTextView.setText(currentNews.getmTitle());
        typeOfNews.setText(currentNews.getmType());

        return listitemview;
    }
}
