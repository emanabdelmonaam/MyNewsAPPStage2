package com.example.android.mynewsappstage2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyNewsAdapter extends ArrayAdapter<News> {

    private static final String LOCATION_SEPARATOR = " of ";

    public MyNewsAdapter(Context context, ArrayList<News>newsArrayList) {
        super(context, 0, newsArrayList);

    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item_view, parent, false);
        }

        // Get the object located at this position in the list
        News currentmyNews = getItem(position);

        // Find the TextView with view ID
        TextView textViewTitle =(TextView) listItemView.findViewById(R.id.text_title);
        textViewTitle.setText(currentmyNews.getmTitle());

        TextView textViewType =(TextView) listItemView.findViewById(R.id.text_section);
        textViewType.setText(currentmyNews.getmType());

        TextView textViewSection =(TextView) listItemView.findViewById(R.id.text_type);
        textViewSection.setText(currentmyNews.getmSection());

        //TextView textViewDate =(TextView) listItemView.findViewById(R.id.text_date);
        // textViewDate.setText(currentmyNews.getmDate());

        // Find the TextView with view ID date
        TextView dateView = null;
        TextView timeView = null;
        if (currentmyNews.getmDate() != null) {
            dateView = listItemView.findViewById(R.id.text_date);
            // Format the date string (i.e. "Mar 3, 1984")
            String formattedDate = formatDate(currentmyNews.getmDate()).concat(",");
            // Display the date of the current earthquake in that TextView
            dateView.setText(formattedDate);

            // Find the TextView with view ID time
            timeView = listItemView.findViewById(R.id.text_time);
            // Format the time string (i.e. "4:30PM")
            String formattedTime = formatTime(currentmyNews.getmDate());
            // Display the time of the current earthquake in that TextView
            timeView.setText(formattedTime);

            //Set date & time views as visible
            dateView.setVisibility(View.VISIBLE);
            timeView.setVisibility(View.VISIBLE);
        } else {
            //Set date & time views as gone
            dateView.setVisibility(View.GONE);
            timeView.setVisibility(View.GONE);
        }

        TextView textViewAuthor = (TextView) listItemView.findViewById(R.id.text_author);
        textViewAuthor.setText(currentmyNews.getmAuthor());

        return listItemView;
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}