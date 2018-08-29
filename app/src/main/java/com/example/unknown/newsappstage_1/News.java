package com.example.unknown.newsappstage_1;

public class News {
    private String mTitle;
    private String mContent;
    private String mType;
    private String mUrl;
    private String mDate;
    private String mAuthor;

    public News(String title, String type, String content, String url, String date, String author) {
        mTitle = title;
        mContent = content;
        mType = type;
        mUrl = url;
        mDate = date;
        mAuthor = author;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmType() {
        return mType;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
