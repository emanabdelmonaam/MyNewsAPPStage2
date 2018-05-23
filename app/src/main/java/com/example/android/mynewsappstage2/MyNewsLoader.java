package com.example.android.mynewsappstage2;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class MyNewsLoader extends AsyncTaskLoader<List<News>> {

    //Query URL
    private String mUrl;

    public MyNewsLoader(Context context, String vUrl) {
        super(context);
        this.mUrl = vUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchMyNewsData(mUrl);
    }

}