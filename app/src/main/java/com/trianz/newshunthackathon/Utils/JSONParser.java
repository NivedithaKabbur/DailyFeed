package com.trianz.newshunthackathon.Utils;

import com.trianz.newshunthackathon.NewsDetails;
import com.trianz.newshunthackathon.NewsSourceItem;

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
                String news_author = itemArrayObj.get("author").toString();
                String news_published_at = itemArrayObj.get("publishedAt").toString();
                String news_url_to_image = itemArrayObj.get("urlToImage").toString();
                String news_description = itemArrayObj.get("description").toString();
                String news_url = itemArrayObj.get("url").toString();

                newsDetails.setTitle(news_title);
                newsDetails.setAuthor(news_author);
                newsDetails.setPublishedAt(news_published_at);
                newsDetails.setUrlToImage(news_url_to_image);
                newsDetails.setDescription(news_description);
                newsDetails.setUrl(news_url);

                newsDetailsItemArrayList.add(newsDetails);

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

        return newsDetailsItemArrayList;
    }

    public ArrayList<NewsSourceItem> newsSourceParser(String jsonData)
    {
        ArrayList<NewsSourceItem> newsSourceItemArrayList = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(jsonData.toString());

            JSONArray sourcesList = (JSONArray) jsonObject.get("sources");


            for (int i=0; i<sourcesList.length(); i++)
            {
                NewsSourceItem newsSourceItem = new NewsSourceItem();

                JSONObject itemArrayObj = sourcesList.getJSONObject(i);

                String news_source_id = itemArrayObj.get("id").toString();
                String news_source_name = itemArrayObj.get("name").toString();
                String news_source_url = itemArrayObj.get("url").toString();

                JSONObject urlsToLogos = itemArrayObj.getJSONObject("urlsToLogos");
                String news_source_logo = urlsToLogos.get("medium").toString();

                newsSourceItem.setSourceId(news_source_id);
                newsSourceItem.setSourceName(news_source_name);
                newsSourceItem.setSourceLogo(news_source_logo);
                newsSourceItem.setSourceUrl(news_source_url);

                newsSourceItemArrayList.add(newsSourceItem);

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

        return newsSourceItemArrayList;
    }

}
