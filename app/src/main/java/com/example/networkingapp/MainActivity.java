package com.example.networkingapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;



import java.io.IOException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    wordAdapter adapter;

    ExecutorService executorService= Executors.newSingleThreadExecutor();
    Handler handler=new Handler(Looper.getMainLooper());
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String USGS_REQUEST_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    private TextView mEmptyStateTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView earthquakeListView=(ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        new Thread(myrunnable).start();

    }

    interface myinterface{
        void myreturn(ArrayList<words> result);
    }


   final myinterface interfacePractice=new myinterface() {
        @Override
        public void myreturn(ArrayList<words> result) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected =  activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    // If network active start fetching data
                    if (isConnected) {
                        mEmptyStateTextView.setText(R.string.no_earthquakes);
                        Log.i("Information","Calling initLoader()");
                        loadingIndicator.setVisibility(View.VISIBLE);

                    } else if(! isConnected){
                        mEmptyStateTextView.setText("No internet connection");
                    }
                    loadingIndicator.setVisibility(View.GONE);
                    updateUI(result);
                }
            });
        }
    };

    Runnable myrunnable=new Runnable() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.VISIBLE);
                }
            });

            ArrayList<words> result=null;
            try {
                result = QueryUtils.fetchEarthQuakeData(USGS_REQUEST_URL);

            } catch (IOException e) {
                e.printStackTrace();
            }
            interfacePractice.myreturn(result);
        }
    };

   public void updateUI(ArrayList<words> result){
       ListView earthquakeListView = (ListView) findViewById(R.id.list);

       // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new wordAdapter(this, result);

        // Set the adapter on the {@link ListView}
       // so the list can be populated in the user interface
       earthquakeListView.setAdapter(adapter);

       earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                words current= adapter.getItem(position);
                Uri earthquakeUri = Uri.parse(current.getMurl());

               // Create a new intent to view the earthquake URI
               Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
   }
    }
