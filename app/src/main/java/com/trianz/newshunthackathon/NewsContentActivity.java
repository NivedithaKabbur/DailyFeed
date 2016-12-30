package com.trianz.newshunthackathon;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class NewsContentActivity extends AppCompatActivity {

    ImageView news_image;
    TextView news_title, news_content;
    Button news_url_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        String newsImage =  getIntent().getStringExtra("newsItemImage");
        String newsTitle =  getIntent().getStringExtra("newsItemTitle");
        String newsContent =  getIntent().getStringExtra("newsItemContent");
        final String newsUrl = getIntent().getStringExtra("newsItemUrl");

        news_image = (ImageView) findViewById(R.id.news_description_image);
        news_title = (TextView) findViewById(R.id.news_description_title);
        news_content = (TextView) findViewById(R.id.news_description_content);
        news_url_button = (Button) findViewById(R.id.news_description_link_button);

        getSupportActionBar().hide();

        Picasso.with(this).load(newsImage).placeholder(R.drawable.placeholder).fit().into(news_image);

        news_title.setText(newsTitle);
        news_content.setText(newsContent);
        news_content.setMovementMethod(new ScrollingMovementMethod());

        news_url_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(newsUrl); // news url link
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
}
