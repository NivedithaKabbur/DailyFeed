package com.trianz.newshunthackathon;


import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

import android.widget.ProgressBar;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.trianz.newshunthackathon.Utils.JSONParser;
import com.trianz.newshunthackathon.Utils.MySingleton;
import com.trianz.newshunthackathon.Utils.RecyclerItemClickListener;

import org.json.JSONObject;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;

public class NewsHeadlines extends AppCompatActivity {


    ArrayList<NewsDetails> newsDetailsArray, newsDetailsSearchResult;
    private RecyclerView recyclerView = null;
    private NewsListAdapter newsListAdapter= null;
    ProgressBar progressBar;
    ImageView errorImage;
    String news_source, news_source_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_news_headlines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       news_source = getIntent().getStringExtra("newsSourceId");
        news_source_name = getIntent().getStringExtra("newsSourceName");

        newsDetailsArray = new ArrayList<>();
        newsDetailsSearchResult = new ArrayList<>();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorImage = (ImageView) findViewById(R.id.error_image);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(news_source_name);

        // Fetch the latest news from the server
        fetchLatestNews(news_source);

    }

    public void fetchLatestNews(String newsSource)
    {

        progressBar.setVisibility(View.VISIBLE);
        errorImage.setVisibility(View.INVISIBLE);

        newsDetailsArray.clear();
        newsDetailsSearchResult.clear();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,"https://newsapi.org/v1/articles?source="+newsSource+"&apiKey=00d0a25c744241ef9d90e4a0572b84aa", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.INVISIBLE);

                        JSONParser jsonParser = new JSONParser();
                        newsDetailsArray =  jsonParser.newsDataParser(response.toString());

                        // set news details to the adapter.
                        recyclerViewSetter();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        error.printStackTrace();
                        progressBar.setVisibility(View.INVISIBLE);
                        errorImage.setVisibility(View.VISIBLE);

                    }
                });


        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

    }

    public void recyclerViewSetter()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        newsListAdapter = new NewsListAdapter(NewsHeadlines.this, newsDetailsArray);
        GridLayoutManager gm = new GridLayoutManager(NewsHeadlines.this, 2);
        recyclerView.setLayoutManager(gm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsListAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        Intent newsContent = new Intent(NewsHeadlines.this, NewsContentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        newsContent.putExtra("newsItemImage", newsDetailsArray.get(position).getUrlToImage());
                        newsContent.putExtra("newsItemTitle", newsDetailsArray.get(position).getTitle());
                        newsContent.putExtra("newsItemContent", newsDetailsArray.get(position).getDescription());
                        newsContent.putExtra("newsItemUrl", newsDetailsArray.get(position).getUrl());
                        newsContent.putExtra("newsItemPublishedAt", newsDetailsArray.get(position).getPublishedAt());

                        startActivity(newsContent);
                    }
                })
        );

    }

}
