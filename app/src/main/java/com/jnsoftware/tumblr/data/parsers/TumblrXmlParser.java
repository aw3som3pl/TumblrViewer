package com.jnsoftware.tumblr.data.parsers;

import android.util.Xml;

import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;
import com.jnsoftware.tumblr.utils.CommonUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TumblrXmlParser {

    private static final String ns = null;

    public List<TumblrPost> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readRawStream(parser);
        } finally {
            in.close();
        }
    }

    private List<TumblrPost> readRawStream(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "tumblr");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if ("posts".equals(name)) { // looks for username
                return readPostsList(parser);
            } else {
                skip(parser);
            }
        }
        throw new XmlPullParserException("Raw XML corrupted");
    }

    private List<TumblrPost> readPostsList(XmlPullParser parser) throws XmlPullParserException, IOException {

        List<TumblrPost> posts = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "posts");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if ("post".equals(name)) { // looks for posts
                parser.require(XmlPullParser.START_TAG, ns, "post");
                String creationTimestamp = parser.getAttributeValue(null,"unix-timestamp");
                String postUrl = parser.getAttributeValue(null,"url");
                TumblrPost post = readPost(parser,creationTimestamp,postUrl);
                if(post!=null) {
                    posts.add(post);
                }
            } else {
                skip(parser);
            }
        }
        return posts;
    }

    private TumblrPost readPost(XmlPullParser parser,String creationTimestamp, String tumblrPostUrl) throws XmlPullParserException, IOException {

        TumblrPost tumblrPost = new TumblrPost(tumblrPostUrl, creationTimestamp);

        parser.require(XmlPullParser.START_TAG, ns, "post");

        String type = parser.getAttributeValue(null,"type");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if(parser.getName().equals("tumblelog")) {
                tumblrPost.setUserName(parser.getAttributeValue(null, "name"));
                tumblrPost.setAccountURL(parser.getAttributeValue(null, "url"));
                tumblrPost.setAvatarURL(parser.getAttributeValue(null, "avatar-url-64"));
            }

            String name = parser.getName();

                switch (type) {
                    case "regular":
                        parser.next();
                        return readRegularPost(parser, tumblrPost);
                    case "link":
                        parser.next();
                        return readLinkPost(parser, tumblrPost);
                    case "quote":
                        parser.next();
                        return readQuotePost(parser, tumblrPost);
                    case "photo":
                        parser.next();
                        return readPhotoPost(parser, tumblrPost);
                    case "conversation":
                        parser.next();
                        return readConversationPost(parser, tumblrPost);
                     case "video":
                         parser.next();
                        return readVideoPost(parser, tumblrPost);
                    case "audio":
                        parser.next();
                        return readAudioPost(parser, tumblrPost);
                    default:
                        skip(parser);
                }
        }
        return null;
    }

    private TumblrPost readRegularPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        initializedPost.determinePostType(TumblrPost.REGULAR);

        initializedPost.setTypeRegular(new TumblrPost.Regular());

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch(name){
                case "regular-title": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "regular-title");
                    initializedPost.getTypeRegular().setRegularTitle(CommonUtils.html2text(readText(parser)));
                    parser.require(XmlPullParser.END_TAG, ns, "regular-title");
                    break;
                case "regular-body": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "regular-body");
                    initializedPost.getTypeRegular().setRegularBody(CommonUtils.html2text(readText(parser)));
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
        return initializedPost;

    }

    private TumblrPost readLinkPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        initializedPost.determinePostType(TumblrPost.LINK);

        initializedPost.setTypeLink(new TumblrPost.Link());

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch(name){
                case "link-text": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "link-text");
                    initializedPost.getTypeLink().setLinkTitle(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "link-text");
                    break;
                case "link-url": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "link-url");
                    initializedPost.getTypeLink().setLinkURL(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "link-url");
                    break;
                case "link-description": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "link-description");
                    initializedPost.getTypeLink().setLinkDescription(CommonUtils.html2text(readText(parser)));
                    parser.require(XmlPullParser.END_TAG, ns, "link-description");
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

        return initializedPost;
    }

    private TumblrPost readQuotePost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        initializedPost.determinePostType(TumblrPost.QUOTE);

        initializedPost.setTypeQuote(new TumblrPost.Quote());

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }


            String name = parser.getName();

            switch(name){
                case "quote-text": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "quote-text");
                    initializedPost.getTypeQuote().setQuoteText(CommonUtils.html2text(readText(parser)));
                    parser.require(XmlPullParser.END_TAG, ns, "quote-text");
                    break;
                case "quote-source": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "quote-source");
                    initializedPost.getTypeQuote().setQuoteSource(CommonUtils.html2text(readText(parser)));
                    parser.require(XmlPullParser.END_TAG, ns, "quote-source");
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

        return initializedPost;
    }

    private TumblrPost readPhotoPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        initializedPost.determinePostType(TumblrPost.PHOTO);

        initializedPost.setTypePhoto(new TumblrPost.Photo());

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }


            String name = parser.getName();

            switch(name){
                case "photo-caption": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "photo-caption");
                    initializedPost.getTypePhoto().setPhotoCaption(CommonUtils.html2text(readText(parser)));
                    parser.require(XmlPullParser.END_TAG, ns, "photo-caption");
                    break;
                case "photo-url": // looks for post content
                    if(Integer.parseInt(parser.getAttributeValue(null,"max-width")) == 250){
                        parser.require(XmlPullParser.START_TAG, ns, "photo-url");
                        initializedPost.getTypePhoto().setMainPhotoURL(readText(parser));
                        parser.require(XmlPullParser.END_TAG, ns, "photo-url");
                    }else{
                        skip(parser);
                    }
                    break;
                case "photoset": // looks for post content
                    initializedPost.getTypePhoto().setPhotoUrlList(readPhotosetUrls(parser, initializedPost));
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
        return initializedPost;
    }

    private TumblrPost readConversationPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        initializedPost.determinePostType(TumblrPost.CONVERSATION);

        initializedPost.setTypeConversation(new TumblrPost.Conversation());

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            switch(name){
                case "conversation": // looks for post content
                    initializedPost.getTypeConversation().setConversationLineList(readConversationLine(parser));
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
        return initializedPost;
    }

    private TumblrPost readVideoPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {
        initializedPost.determinePostType(TumblrPost.VIDEO);

        initializedPost.setTypeVideo(new TumblrPost.Video());

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            switch(name){
                case "video-caption": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "video-caption");
                    initializedPost.getTypeVideo().setVideoCaption(CommonUtils.html2text(readText(parser)));
                    parser.require(XmlPullParser.END_TAG, ns, "video-caption");
                    break;
                case "video-player": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "video-player");
                    List<String> extractedUrls = CommonUtils.extractUrls(readText(parser));
                    initializedPost.getTypeVideo().setVideoThumbnailUrl(extractedUrls.get(0));
                    initializedPost.getTypeVideo().setVideoSourceURL(extractedUrls.get(1));
                    parser.require(XmlPullParser.END_TAG, ns, "video-player");
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

        return initializedPost;
    }

    private TumblrPost readAudioPost(XmlPullParser parser, TumblrPost initializedPost) throws IOException, XmlPullParserException {

        initializedPost.determinePostType(TumblrPost.AUDIO);

        initializedPost.setTypeAudio(new TumblrPost.Audio());

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            switch(name){
                case "audio-caption": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "audio-caption");
                    initializedPost.getTypeAudio().setAudioCaption(CommonUtils.html2text(readText(parser)));
                    parser.require(XmlPullParser.END_TAG, ns, "audio-caption");
                    break;
                case "audio-player": // looks for post content
                    initializedPost.getTypeAudio().setAudioURL(CommonUtils.extractUrls(readText(parser)).get(0));
                    break;
                case "id3-artist": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "id3-artist");
                    initializedPost.getTypeAudio().setArtist(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "id3-artist");
                    break;
                case "id3-album": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "id3-album");
                    initializedPost.getTypeAudio().setAlbum(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "id3-album");
                    break;
                case "id3-year": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "id3-year");
                    initializedPost.getTypeAudio().setYear(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "id3-year");
                    break;
                case "id3-track": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "id3-track");
                    initializedPost.getTypeAudio().setTrack(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "id3-track");
                    break;
                case "id3-title": // looks for post content
                    parser.require(XmlPullParser.START_TAG, ns, "id3-title");
                    initializedPost.getTypeAudio().setTitle(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "id3-title");
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

        return initializedPost;
    }

    private List<String> readPhotosetUrls(XmlPullParser parser, TumblrPost initializedPost) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "photoset");

        List<String> photosetList = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if ("photo".equals(name)) { // looks for photoset entry container
                    parser.require(XmlPullParser.START_TAG, ns, "photo");
                    String photosetEntryUrl = readPhotosetEntry(parser);
                    if(photosetEntryUrl!=null){
                        photosetList.add(photosetEntryUrl);
                    }
            } else {
                skip(parser);
            }
        }
        return photosetList;
    }

    private String readPhotosetEntry(XmlPullParser parser) throws XmlPullParserException, IOException{

        String photoUrl ="";

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if(Integer.parseInt(parser.getAttributeValue(null,"max-width")) == 250){
                parser.require(XmlPullParser.START_TAG, ns, "photo-url");
                photoUrl =  readText(parser);
                parser.require(XmlPullParser.END_TAG, ns, "photo-url");
            }else{
                skip(parser);
            }
        }
        return photoUrl;
    }

    private List<String> readConversationLine(XmlPullParser parser) throws XmlPullParserException, IOException{

        List<String> conversationLineList = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            parser.require(XmlPullParser.START_TAG, ns, "line");
            String senderName = parser.getAttributeValue(null,"label");
            String message = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "line");

            conversationLineList.add(senderName + " " + message);
        }
        return conversationLineList;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
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