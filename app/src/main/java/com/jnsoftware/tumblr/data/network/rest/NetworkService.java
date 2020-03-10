package com.jnsoftware.tumblr.data.network.rest;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public interface NetworkService {
    /**
     * @return Observable feed response
     */
    @GET("https://{user}.tumblr.com/api/read")
    Single<ResponseBody> getTumblrPostList(@Path("user") String user);
}
