package com.quantumfin.daori.listusergithub;

import com.squareup.okhttp.Call;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by daori on 12/15/14.
 */
public interface GithubAPI {
    @GET("/users")
    void userList(Callback<List<Users>> callback);
}
