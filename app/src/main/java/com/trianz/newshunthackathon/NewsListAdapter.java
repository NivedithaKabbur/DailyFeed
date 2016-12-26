package com.trianz.newshunthackathon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by niveditha.kabbur on 22-12-2016.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {

    private List<NewsDetails> newsDetailsList;
    Context context;

    public NewsListAdapter(Context context, List<NewsDetails> newsDetailsList) {

        this.context = context;
        this.newsDetailsList = newsDetailsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.bindContent(newsDetailsList.get(position));

      /*  NewsDetails newsDetails = newsDetailsList.get(position);
        holder.title.setText(newsDetails.getTitle());
        holder.source.setText(newsDetails.getSource());

        Picasso
                .with(context)
              //  .load(newsDetails.getImage())
                .load("http://www.trianz.com/images/home-logo.png")
                .fit() // will explain later
                .into(holder.image);   */

    }

    @Override
    public int getItemCount() {
       return newsDetailsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, source;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.news_item_title);
            source = (TextView) view.findViewById(R.id.news_item_source);
            image = (ImageView) view.findViewById(R.id.news_item_image);
        }

        public void bindContent(NewsDetails newsDetails) {
            title.setText(newsDetails.getTitle());
            source.setText(newsDetails.getSource());
            Picasso.with(context).load(newsDetails.getImage()).fit().placeholder(R.drawable.placeholder).fit().into(image);

        }
    }
}
