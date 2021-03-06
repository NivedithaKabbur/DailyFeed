package com.trianz.newshunthackathon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
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

            if(!newsDetails.getTitle().equals("null")) {
                title.setText(newsDetails.getTitle());
            }
            if(!newsDetails.getAuthor().equals("null")) {
                source.setText(newsDetails.getAuthor());
            }

            if(!newsDetails.getUrlToImage().equals("null") && !newsDetails.getUrlToImage().equals("")) {
                Picasso.with(context).setLoggingEnabled(true);
                Picasso.with(context).load(newsDetails.getUrlToImage()).fit().into(image);
            }
        }
    }
}
