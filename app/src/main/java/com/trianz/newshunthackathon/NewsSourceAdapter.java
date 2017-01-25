package com.trianz.newshunthackathon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by niveditha.kabbur on 30-12-2016.
 */
public class NewsSourceAdapter  extends RecyclerView.Adapter<NewsSourceAdapter.MyViewHolder> {

    private List<NewsSourceItem> newsSourceItemList;
    Context context;

    public NewsSourceAdapter(Context context, List<NewsSourceItem> newsSourceItemList) {

        this.context = context;
        this.newsSourceItemList = newsSourceItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_source_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.bindContent(newsSourceItemList.get(position));

    }

    @Override
    public int getItemCount() {
        return newsSourceItemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.news_src_name);
            image = (ImageView) view.findViewById(R.id.news_src_image);
        }

        public void bindContent(NewsSourceItem newsSourceItem) {

            name.setText(newsSourceItem.getSourceName());
            Picasso.with(context).load(newsSourceItem.getSourceLogo()).resize(300,300).networkPolicy(NetworkPolicy.OFFLINE).centerInside().into(image);

        }
    }
}
