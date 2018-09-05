package com.example.unknown.newsappstage_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        TextView date = (TextView) listitemview.findViewById(R.id.date);
        TextView author = (TextView) listitemview.findViewById(R.id.author_name);
        mainTextView.setText(currentNews.getmContent());
        titleTextView.setText(currentNews.getmTitle());
        typeOfNews.setText(currentNews.getmType());
        date.setText(getDate(currentNews.getmDate()));
        author.setText(currentNews.getmAuthor());

        return listitemview;
    }

    public String getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(d);

    }
}
