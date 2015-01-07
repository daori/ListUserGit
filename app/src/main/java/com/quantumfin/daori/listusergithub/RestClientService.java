package com.quantumfin.daori.listusergithub;

import retrofit.RestAdapter;

/**
 * Created by daori on 12/15/14.
 */
public class RestClientService {

    private static final String API_END_POINT = "https://api.github.com";

    public static GithubAPI getService(){
        return new RestAdapter.Builder()
                .setEndpoint(API_END_POINT)
                .build().create(GithubAPI.class);

    }
}
