package com.example.travelblog.http;

import java.util.ArrayList;
import java.util.List;

public class BLogData {

    private List<Blog> data;

    public List<Blog> getData(){
        return data!= null ? data : new ArrayList<>();
    }
}
