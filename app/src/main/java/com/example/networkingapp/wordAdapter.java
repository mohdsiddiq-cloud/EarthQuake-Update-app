package com.example.networkingapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class wordAdapter extends ArrayAdapter<words> {


    private static final String LOCATION_SEPARATOR = " of ";
    String primaryLocation;
    String locationOffset;
    public wordAdapter(Activity context, ArrayList<words> type){
        super(context,0,type);

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
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View listItemView=convertView;
        if(listItemView==null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }
        words currentWord = getItem(position);
        TextView textView1 = (TextView) listItemView.findViewById(R.id.text1);
        textView1.setText(currentWord.getMtext1()+"");

        String originalLocation=currentWord.getMtext2();
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.Near_the);
            primaryLocation = originalLocation;
        }
        TextView location=(TextView) listItemView.findViewById(R.id.location);
        location.setText(locationOffset);

        TextView textView2=(TextView) listItemView.findViewById(R.id.text2);
        textView2.setText(primaryLocation);

        Date currentdate=new Date(currentWord.getMtime());
        TextView textView3=(TextView) listItemView.findViewById(R.id.text3);
        String mdate= formatDate(currentdate);
        textView3.setText(mdate);

        TextView textView4=(TextView) listItemView.findViewById(R.id.text4);
        String mTime=formatTime(currentdate);
        textView4.setText(mTime);
        return listItemView;
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}



