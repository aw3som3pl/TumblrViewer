package com.jnsoftware.tumblr.data.network.rest;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.ResponseBody;

@Singleton
public class RestApiManager implements RestApiHelper {

    NetworkService mService;

    @Inject
    public RestApiManager(NetworkService apiService) {
        mService = apiService;
    }

    @Override
    public Single<ResponseBody> getTumblrPostXmlStream(String user, Map<String,String> params) {
        return mService.getTumblrPostList(user,params);
    }
}
