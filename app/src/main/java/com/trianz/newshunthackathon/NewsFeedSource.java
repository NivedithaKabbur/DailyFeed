package com.trianz.newshunthackathon;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.trianz.newshunthackathon.Utils.JSONParser;
import com.trianz.newshunthackathon.Utils.MySingleton;
import com.trianz.newshunthackathon.Utils.RecyclerItemClickListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NewsFeedSource extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<NewsSourceItem> newsSourceItemArray;
    private RecyclerView recyclerView = null;
    private NewsSourceAdapter newsSourceAdapter= null;
    ProgressBar progressBar;
    ImageView errorImage;
    Button business, entertainment,general, sports, technology;
    String NEWS_CATEGORY = "general";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_source);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setSubtitle("Powered by newsAPI.org");

        newsSourceItemArray = new ArrayList<>();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                fetchLatestNews("business");
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                fetchLatestNews("entertainment");
            }
        });


        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                fetchLatestNews("general");
            }
        });


        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                fetchLatestNews("sport");
            }
        });

        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();
                fetchLatestNews("technology");
            }
        });


        // Fetch the latest news from the server
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        progressBar.setVisibility(View.VISIBLE);
        errorImage.setVisibility(View.INVISIBLE);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET,"https://newsapi.org/v1/sources?language=en&category="+category, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.INVISIBLE);

                        JSONParser jsonParser = new JSONParser();
                        newsSourceItemArray =  jsonParser.newsSourceParser(response.toString());

                        Log.v("data",newsSourceItemArray.get(2).getSourceLogo().toString());

                        // set news details to the adapter.
                        recyclerViewSetter(newsSourceItemArray);

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

    public void recyclerViewSetter(final ArrayList<NewsSourceItem> newsSourceItemList)
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        newsSourceAdapter = new NewsSourceAdapter(NewsFeedSource.this, newsSourceItemList);
        GridLayoutManager gm = new GridLayoutManager(NewsFeedSource.this, 1);
        recyclerView.setLayoutManager(gm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsSourceAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        Intent newsContent = new Intent(NewsFeedSource.this, NewsHeadlines.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        newsContent.putExtra("newsSourceId", newsSourceItemList.get(position).getSourceId());

                        startActivity(newsContent);
                    }
                })
        );

    }

}
