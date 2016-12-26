package com.trianz.newshunthackathon.Utils;

import android.util.Log;

import com.trianz.newshunthackathon.NewsDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by niveditha.kabbur on 22-12-2016.
 */
public class JSONParser {

    public ArrayList<NewsDetails> newsDataParser(String jsondata)
    {

        ArrayList<NewsDetails> newsDetailsItemArrayList = new ArrayList<>();


        try {

            JSONObject jsonObject = new JSONObject(jsondata.toString());

            JSONArray articlesList = (JSONArray) jsonObject.get("articles");


            for (int i=0; i<articlesList.length(); i++)
            {
                NewsDetails newsDetails = new NewsDetails();

                JSONObject itemArrayObj = articlesList.getJSONObject(i);

                String news_title = itemArrayObj.get("title").toString();
                String news_source = itemArrayObj.get("source").toString();
                String news_category = itemArrayObj.get("category").toString();
                String news_image = itemArrayObj.get("image").toString();
                String news_content = itemArrayObj.get("content").toString();
                String news_url = itemArrayObj.get("url").toString();

                newsDetails.setTitle(news_title);
                newsDetails.setSource(news_source);
                newsDetails.setCategory(news_category);
                newsDetails.setImage(news_image);
                newsDetails.setContent(news_content);
                newsDetails.setUrl(news_url);

                newsDetailsItemArrayList.add(newsDetails);

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

        return newsDetailsItemArrayList;
    }

}
