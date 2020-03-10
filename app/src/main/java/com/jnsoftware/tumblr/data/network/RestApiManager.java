package com.jnsoftware.tumblr.data.network;

import com.jnsoftware.tumblr.data.network.parsers.TumblrXmlParser;
import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;
import com.jnsoftware.tumblr.data.network.pojo.WrapperResponse;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    public Single<WrapperResponse<ResponseBody>> getTumblrPostList(String user) {
        return mService.getTumblrPostList(user);
    }
}
