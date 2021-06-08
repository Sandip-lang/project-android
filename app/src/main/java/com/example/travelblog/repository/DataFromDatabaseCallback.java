package com.example.travelblog.repository;

import com.example.travelblog.http.Blog;

import java.util.List;

public interface DataFromDatabaseCallback {
    void onSuccess(List<Blog> BlogList);
}
