package com.jnsoftware.tumblr.data.network.parsers;

import android.util.Xml;

import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TumblrXmlParser {

    private static final String ns = null;

    public List<TumblrPost> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<TumblrPost> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<TumblrPost> posts = new ArrayList<>();
        String feedOwnerName = "";

        parser.require(XmlPullParser.START_TAG, ns, "tumblr");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch(name){
                case "tumblelog": // looks for username
                    feedOwnerName = parser.getAttributeValue("name",null);
                    break;
                case "post": // looks for posts
                    posts.add(readPost(parser, feedOwnerName));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return posts;
    }

    private TumblrPost readPost(XmlPullParser parser, String feedOwnerName) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "post");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String type = parser.getAttributeValue("type",null);

            switch (type) {
                case "regular":
                    return readRegularPost(parser, initializeMetaData(parser, feedOwnerName));
                case "link":
                    return readLinkPost(parser, initializeMetaData(parser, feedOwnerName));
                case "quote":
                    return readQuotePost(parser, initializeMetaData(parser, feedOwnerName));
                /*case "photo":
                    return readPhotoPost(parser, initializeMetaData(parser, feedOwnerName));
                case "conversation":
                    return readConversationPost(parser, initializeMetaData(parser, feedOwnerName));
                case "video":
                    return readVideoPost(parser, initializeMetaData(parser, feedOwnerName));
                case "audio":
                    return readAudioPost(parser, initializeMetaData(parser, feedOwnerName));
                case "answer":
                    return readAnswerPost(parser, initializeMetaData(parser, feedOwnerName));
                 */
                default:
                    skip(parser);
            }
        }
        throw new XmlPullParserException("Read post failure");
    }

    private TumblrPost initializeMetaData(XmlPullParser parser, String feedOwnerName) throws IOException, XmlPullParserException {
        String tumblrPostURL = parser.getAttributeValue("url",null);
        String postCreationTimestamp = parser.getAttributeValue("type",null);
        parser.next();
        return new TumblrPost(feedOwnerName, tumblrPostURL, postCreationTimestamp);
    }

    private TumblrPost readRegularPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        String regularTitle = "";
        String regularBody = "";

        parser.require(XmlPullParser.START_TAG, ns, "post");

        initializedPost.determinePostType(TumblrPost.REGULAR);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch(name){
                case "tumblelog": // looks for user avatar
                    parser.require(XmlPullParser.START_TAG, ns, "tumblelog");
                    initializedPost.setAccountURL(parser.getAttributeValue("url",null));
                    initializedPost.setAvatarURL(parser.getAttributeValue("avatar-url-64",null));
                    break;
                case "regular-title": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "regular-title");
                    regularTitle = readText(parser);
                    parser.require(XmlPullParser.END_TAG, ns, "regular-title");
                    break;
                case "regular-body": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "regular-body");
                    regularBody = readText(parser);
                    parser.require(XmlPullParser.END_TAG, ns, "regular-body");
                    break;
                case "tag": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "tag");
                    initializedPost.addTag(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "tag");
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        if(!regularBody.isEmpty() && !regularTitle.isEmpty()) {
            initializedPost.setTypeRegular(new TumblrPost.Regular(regularTitle, regularBody));
            return initializedPost;
        }else{
            throw new XmlPullParserException("Regular post parse failed");
        }
    }

    private TumblrPost readLinkPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        String linkText = "";
        String linkURL = "";
        String linkDescription = "";

        parser.require(XmlPullParser.START_TAG, ns, "post");

        initializedPost.determinePostType(TumblrPost.LINK);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch(name){
                case "tumblelog": // looks for user avatar
                    parser.require(XmlPullParser.START_TAG, ns, "tumblelog");
                    initializedPost.setAccountURL(parser.getAttributeValue("url",null));
                    initializedPost.setAvatarURL(parser.getAttributeValue("avatar-url-64",null));
                    break;
                case "link-text": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "link-text");
                    linkText = readText(parser);
                    parser.require(XmlPullParser.END_TAG, ns, "link-text");
                    break;
                case "link-url": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "link-url");
                    linkURL = readText(parser);
                    parser.require(XmlPullParser.END_TAG, ns, "link-text");
                    break;
                case "link-description": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "link-description");
                    linkDescription = readText(parser);
                    parser.require(XmlPullParser.END_TAG, ns, "link-text");
                    break;
                case "tag": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "tag");
                    initializedPost.addTag(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "link-text");
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        if(!linkText.isEmpty() && !linkURL.isEmpty() && !linkDescription.isEmpty()) {
            initializedPost.setTypeLink(new TumblrPost.Link(linkText, linkURL, linkDescription));
            return initializedPost;
        }else{
            throw new XmlPullParserException("Link post parse failed");
        }
    }

    private TumblrPost readQuotePost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        String quoteText = "";
        String quoteSource = "";

        parser.require(XmlPullParser.START_TAG, ns, "post");

        initializedPost.determinePostType(TumblrPost.QUOTE);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch(name){
                case "tumblelog": // looks for user avatar
                    parser.require(XmlPullParser.START_TAG, ns, "tumblelog");
                    initializedPost.setAccountURL(parser.getAttributeValue("url",null));
                    initializedPost.setAvatarURL(parser.getAttributeValue("avatar-url-64",null));
                    break;
                case "quote-text": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "quote-text");
                    quoteText = readText(parser);
                    parser.require(XmlPullParser.END_TAG, ns, "quote-text");
                    break;
                case "quote-source": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "quote-source");
                    quoteSource = readText(parser);
                    parser.require(XmlPullParser.END_TAG, ns, "quote-source");
                    break;
                case "tag": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "tag");
                    initializedPost.addTag(readText(parser));
                    parser.require(XmlPullParser.START_TAG, ns, "tag");
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        if(!quoteText.isEmpty() && !quoteSource.isEmpty()) {
            initializedPost.setTypeQuote(new TumblrPost.Quote(quoteText, quoteSource));
            return initializedPost;
        }else{
            throw new XmlPullParserException("Quote post parse failed");
        }
    }
    /*
    private TumblrPost readPhotoPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        String photoCaption = "";
        String mainPhotoURL = "";
        List<String> photoUrlList;

        parser.require(XmlPullParser.START_TAG, ns, "post");

        initializedPost.determinePostType(TumblrPost.PHOTO);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch(name){
                case "tumblelog": // looks for user avatar
                    parser.require(XmlPullParser.START_TAG, ns, "tumblelog");
                    initializedPost.setAccountURL(parser.getAttributeValue("url",null));
                    initializedPost.setAvatarURL(parser.getAttributeValue("avatar-url-64",null));
                    break;
                case "photo-caption": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "photo-caption");
                    photoCaption = readText(parser);
                    parser.require(XmlPullParser.END_TAG, ns, "photo-caption");
                    break;
                case "photo-url": // looks for post content
                    if(Integer.parseInt(parser.getAttributeValue("max-width",null)) == 250){
                        parser.require(XmlPullParser.START_TAG, ns, "photo-url");
                        mainPhotoURL = readText(parser);
                        parser.require(XmlPullParser.END_TAG, ns, "photo-url");
                    }
                    break;
                case "photoset": // looks for post content

                    photoUrlList = readPhotosetUrls(parser);

                    break;
                case "tag": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "tag");
                    initializedPost.addTag(readText(parser));
                    parser.require(XmlPullParser.START_TAG, ns, "tag");
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        if(!quoteText.isEmpty() && !quoteSource.isEmpty()) {
            initializedPost.setTypeQuote(new TumblrPost.Quote(quoteText, quoteSource));
            return initializedPost;
        }else{
            throw new XmlPullParserException("Quote post parse failed");
        }
    }

    private TumblrPost readConversationPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

    }

    private TumblrPost readVideoPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

    }

    private TumblrPost readAudioPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

    }

    private TumblrPost readAnswerPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

    }

    private List<String> readPhotosetUrls(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "photoset");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if ("photo".equals(name)) { // looks for photoset entry
                parser.require(XmlPullParser.START_TAG, ns, "photo");
                initializedPost.setAccountURL(parser.getAttributeValue("url", null));
                initializedPost.setAvatarURL(parser.getAttributeValue("avatar-url-64", null));
            } else {
                skip(parser);
            }
        }
    }
*/
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")){
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    // Processes summary tags in the feed.
    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return summary;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}