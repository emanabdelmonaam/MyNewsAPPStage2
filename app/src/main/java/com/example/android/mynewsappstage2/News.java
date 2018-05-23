package com.example.android.mynewsappstage2;

import java.util.Date;

public class News {

    private String mTitle;
    private String mType;
    private String mSection;
    private Date mDate;
    private String mAuthor;
    private String mUrl;

    public News(String vTitle, String vType, String vSection, Date vDate, String vAuthor, String vUrl) {

        this.mTitle = vTitle;
        this.mType = vType;
        this.mSection = vSection;
        this.mDate=vDate;
        this.mAuthor = vAuthor;
        this.mUrl = vUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmType() {
        return mType;
    }

    public String getmSection() {
        return mSection;
    }

    public Date getmDate() {
        return mDate;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmUrl() {
        return mUrl;
    }

}