package com.jnsoftware.tumblr.data.network.rest;


import io.reactivex.Single;
import okhttp3.ResponseBody;

public interface RestApiHelper {

    Single<ResponseBody> getTumblrPostXmlStream(String user);
}
