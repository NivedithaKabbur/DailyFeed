package com.trianz.newshunthackathon;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.trianz.newshunthackathon.Utils.JSONParser;
import com.trianz.newshunthackathon.Utils.MySingleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayList<NewsDetails> newsDetailsArray, newsDetailsSearchResult;
    private RecyclerView recyclerView = null;
    private NewsListAdapter newsListAdapter= null;
    private Spinner categorySpinner = null;
    ArrayList<String> category_list = new ArrayList<>();
    Set<String> category_list_set = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newsDetailsArray = new ArrayList<>();
        newsDetailsSearchResult = new ArrayList<>();

        categorySpinner = (Spinner) findViewById(R.id.category_spinner);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Fetch the latest news from the server
        fetchLatestNews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newsDetailsSearchResult.clear();

                for(int i = 0; i< newsDetailsArray.size(); i++)
                {
                    if(newsDetailsArray.get(i).getTitle().toLowerCase().contains(newText.toLowerCase()) || newsDetailsArray.get(i).getSource().toLowerCase().contains(newText.toLowerCase()))
                    {

                      newsDetailsSearchResult.add(newsDetailsArray.get(i));

                    }
                }

                // set news details to the adapter.
                recyclerViewSetter(newsDetailsSearchResult);

                return false;
            }
        });
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

    public void fetchLatestNews()
    {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, /*"https://dailyfeedapi.herokuapp.com/dailyfeed.json"*/"https://api.myjson.com/bins/15vpv3", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONParser jsonParser = new JSONParser();
                        newsDetailsArray =  jsonParser.newsDataParser(response.toString());

                        category_list.clear();
                        category_list_set.clear();

                      for (int i= 0 ; i< newsDetailsArray.size(); i++)
                      {
                          category_list_set.add(newsDetailsArray.get(i).getCategory());
                      }

                        // set news details to the adapter.
                        recyclerViewSetter(newsDetailsArray);
                        categorySpinnerDataSetter();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });


        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

    }

    public void recyclerViewSetter(ArrayList<NewsDetails> newsDetailsList)
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        newsListAdapter = new NewsListAdapter(MainActivity.this, newsDetailsList);
        GridLayoutManager gm = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsListAdapter);
    }

    public void categorySpinnerDataSetter()
    {

        category_list.add("All");
        category_list.addAll(category_list_set);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(this,
                R.layout.category_spinner_layout, category_list);

        // Specify the layout to use when the list of choices appears
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(category_adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                newsDetailsSearchResult.clear();

                if( !categorySpinner.getSelectedItem().toString().equals("All")) {

                    for (int i = 0; i < newsDetailsArray.size(); i++) {
                        if (newsDetailsArray.get(i).getCategory().equals(categorySpinner.getSelectedItem().toString())) {

                            newsDetailsSearchResult.add(newsDetailsArray.get(i));

                        }
                    }

                    // set news details to the adapter.
                    recyclerViewSetter(newsDetailsSearchResult);
                }

                else
                {
                    // set news details to the adapter.
                    recyclerViewSetter(newsDetailsArray);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
