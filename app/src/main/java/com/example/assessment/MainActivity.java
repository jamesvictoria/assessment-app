package com.example.assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.assessment.adapter.NewsAdapter;
import com.example.assessment.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    private RecyclerView recyclerView;
    private List<News> newsList;
    private NewsAdapter adapter;

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        title.setText(getString(R.string.title));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>();
        adapter = new NewsAdapter(this, newsList);
        adapter.setNews(newsList);

        recyclerView.setAdapter(adapter);

        // get api key from https://newsapi.org/
        Request request = new Request.Builder()
                .url("https://newsapi.org/v2/everything?q=apple&from=2022-08-12&to=2022-08-12&sortBy=popularity&apiKey=b66eaf3bd2424ce28a016bd542e468b8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    String responseData = responseBody.string();
                    JSONObject responseObject = new JSONObject(responseData);
                    JSONArray responseArray = responseObject.getJSONArray("articles");
                    newsList.clear();

                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject object = responseArray.getJSONObject(i);
                        News newsObject = new News(i + 1,object.getString("author"), object.getString("title"), object.getString("description"), object.getString("urlToImage"), object.getString("content")) ;
                        newsList.add(newsObject);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}