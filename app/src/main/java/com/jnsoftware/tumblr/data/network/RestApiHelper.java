package com.jnsoftware.tumblr.data.network;

import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;
import com.jnsoftware.tumblr.data.network.pojo.WrapperResponse;


import io.reactivex.Single;
import okhttp3.ResponseBody;

public interface RestApiHelper {

    Single<WrapperResponse<ResponseBody>> getTumblrPostList(String user);
}
