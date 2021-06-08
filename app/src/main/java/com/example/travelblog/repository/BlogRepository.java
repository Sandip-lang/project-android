package com.example.travelblog.repository;

import android.content.Context;

import com.example.travelblog.database.AppDatabase;
import com.example.travelblog.database.BlogDAO;
import com.example.travelblog.database.DatabaseProvider;
import com.example.travelblog.http.BLogHttpClient;
import com.example.travelblog.http.Blog;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlogRepository {

    private BLogHttpClient httpClient;
    private AppDatabase database;
    private Executor executor;

    public BlogRepository(Context context){
        httpClient=BLogHttpClient.INSTANCE;
        database= DatabaseProvider.getInstance(context.getApplicationContext());
        executor= Executors.newSingleThreadExecutor();
    }

    public void loadDataFromDatabase(DataFromDatabaseCallback callback){
        executor.execute(()->callback.onSuccess(database.blogDao().getAll()));
    }

    public void loadDataFromNetwork(DataFromNetworkCallback callback) {
        executor.execute(() -> {
            List<Blog> blogList = httpClient.loadBlogArticles();
            if (blogList == null) {
                callback.onError(); // 1
            } else {
                BlogDAO blogDAO = database.blogDao();
                blogDAO.deleteAll(); // 2
                blogDAO.insertAll(blogList); // 3
                callback.onSuccess(blogList); // 4
            }
        });
    }
}
