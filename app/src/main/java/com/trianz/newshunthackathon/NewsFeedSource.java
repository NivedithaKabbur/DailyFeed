package com.trianz.newshunthackathon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.trianz.newshunthackathon.Utils.JSONParser;
import com.trianz.newshunthackathon.Utils.MySingleton;
import com.trianz.newshunthackathon.Utils.RecyclerItemClickListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFeedSource extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<NewsSourceItem> newsSourceItemArray;
    private RecyclerView recyclerView = null;
    private NewsSourceAdapter newsSourceAdapter= null;
    LottieAnimationView animationView;
    ImageView errorImage, collapseImage;
    Button business, entertainment,general, sports, technology, science, health;
    String NEWS_CATEGORY = "general", NEWS_CATEGORY_NAME = "General";
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_source);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapseImage = (ImageView) collapsingToolbar.findViewById(R.id.header);

        newsSourceItemArray = new ArrayList<>();

        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        errorImage = (ImageView) findViewById(R.id.error_image);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        // Navigation view category item onClick Listeners

        business = (Button) header.findViewById(R.id.news_option_business);
        entertainment = (Button) header.findViewById(R.id.news_option_entertainment);
        general = (Button) header.findViewById(R.id.news_option_general);
        sports = (Button) header.findViewById(R.id.news_option_sport);
        technology = (Button) header.findViewById(R.id.news_option_technology);
        science = (Button) header.findViewById(R.id.news_option_science);
        health = (Button) header.findViewById(R.id.news_option_health);


        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                NEWS_CATEGORY = "business";
                NEWS_CATEGORY_NAME = "Business";
                collapsingToolbar.setTitle(NEWS_CATEGORY_NAME);
                collapseImage.setBackgroundColor(Color.LTGRAY);
                fetchLatestNews(NEWS_CATEGORY);
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                NEWS_CATEGORY = "entertainment";
                NEWS_CATEGORY_NAME = "Entertainment";
                collapsingToolbar.setTitle(NEWS_CATEGORY_NAME);
                collapseImage.setBackgroundColor(Color.BLACK);
                fetchLatestNews(NEWS_CATEGORY);
            }
        });


        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                NEWS_CATEGORY = "general";
                NEWS_CATEGORY_NAME = "General";
                collapsingToolbar.setTitle(NEWS_CATEGORY_NAME);
                collapseImage.setBackgroundColor(Color.WHITE);
                fetchLatestNews(NEWS_CATEGORY);
            }
        });


        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                NEWS_CATEGORY = "sports";
                NEWS_CATEGORY_NAME = "Sports";
                collapsingToolbar.setTitle(NEWS_CATEGORY_NAME);
                collapseImage.setBackgroundColor(Color.YELLOW);
                fetchLatestNews(NEWS_CATEGORY);
            }
        });

        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                NEWS_CATEGORY = "technology";
                NEWS_CATEGORY_NAME = "Technology";
                collapsingToolbar.setTitle(NEWS_CATEGORY_NAME);
                collapseImage.setBackgroundColor(Color.GRAY);
                fetchLatestNews(NEWS_CATEGORY);
            }
        });

        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                NEWS_CATEGORY = "science";
                NEWS_CATEGORY_NAME = "Science";
                collapsingToolbar.setTitle(NEWS_CATEGORY_NAME);
                collapseImage.setBackgroundColor(Color.MAGENTA);
                fetchLatestNews(NEWS_CATEGORY);
            }
        });


        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                NEWS_CATEGORY = "health";
                NEWS_CATEGORY_NAME = "Health";
                collapsingToolbar.setTitle(NEWS_CATEGORY_NAME);
                collapseImage.setBackgroundColor(Color.RED);
                fetchLatestNews(NEWS_CATEGORY);
            }
        });


        // Fetch the latest general news from the server
        collapsingToolbar.setTitle(NEWS_CATEGORY_NAME);
        collapseImage.setBackgroundColor(Color.WHITE);
        fetchLatestNews(NEWS_CATEGORY);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.news_option_business) {

        } else if (id == R.id.news_option_entertainment) {

        } else if (id == R.id.news_option_general) {

        } else if (id == R.id.news_option_technology) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fetchLatestNews(String category)
    {

        animationView.setAnimation("loading_animation.json");
        animationView.playAnimation();

        errorImage.setVisibility(View.INVISIBLE);

        newsSourceItemArray.clear();
        recyclerViewSetter();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,"https://newsapi.org/v2/sources?language=en&category="+category+"&apiKey="+getResources().getString(R.string.api_key), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        animationView.setVisibility(View.INVISIBLE);
                        JSONParser jsonParser = new JSONParser();
                        newsSourceItemArray =  jsonParser.newsSourceParser(response.toString());

                        // set news details to the adapter.
                        recyclerViewSetter();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        error.printStackTrace();
                        animationView.setVisibility(View.INVISIBLE);
                        errorImage.setVisibility(View.VISIBLE);

                    }
                });


        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

    }

    public void recyclerViewSetter()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        newsSourceAdapter = new NewsSourceAdapter(NewsFeedSource.this, newsSourceItemArray);
        GridLayoutManager gm = new GridLayoutManager(NewsFeedSource.this, 1);
        recyclerView.setLayoutManager(gm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsSourceAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        Intent newsContent = new Intent(NewsFeedSource.this, NewsHeadlines.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        newsContent.putExtra("newsSourceId", newsSourceItemArray.get(position).getSourceId());
                        newsContent.putExtra("newsSourceName",  newsSourceItemArray.get(position).getSourceName());

                        startActivity(newsContent);
                    }
                })
        );

    }

}
