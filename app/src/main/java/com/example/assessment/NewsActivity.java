package com.example.assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assessment.model.News;

public class NewsActivity extends AppCompatActivity {
    public static final String NEWS_ID_KEY = "id";

    private TextView txtSingleTitle, txtSingleAuthor, txtSingleContent;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        txtSingleTitle = findViewById(R.id.txtSingleTitle);
        txtSingleAuthor = findViewById(R.id.txtSingleAuthor);
        txtSingleContent = findViewById(R.id.txtSingleContent);
        imageView = findViewById(R.id.imageView);

        News news = new News(1,"Steve Dent", "Facebook and Instagram apps can track users via their in-app browsers", "If you visit a website you see on Facebook and Instagram, you've likely noticed that you're not redirected to your browser of choice but rather a custom in-app browser. It turns out that those browsers inject javascript code into each website visited, allowin…", "https://s.yimg.com/os/creatr-uploaded-images/2022-06/15feae70-f4d0-11ec-b7bf-e1587b438313", "If you visit a website you see on Facebook and Instagram, you've likely noticed that you're not redirected to your browser of choice but rather a custom in-app browser. It turns out that those browse… [+2035 chars]");

//        Intent intent = getIntent();
//        if (intent != null) {
//            int newsId = intent.getIntExtra(NEWS_ID_KEY, -1);
//        }
        setData(news);
    }

    private void setData(News news) {
        txtSingleTitle.setText(news.getTitle());
        txtSingleAuthor.setText(news.getAuthor());
        txtSingleContent.setText(news.getContent());
        Glide.with(this)
                .asBitmap().load(news.getUrlToImage())
                .into(imageView);
    }
}