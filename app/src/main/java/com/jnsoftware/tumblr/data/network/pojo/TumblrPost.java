package com.jnsoftware.tumblr.data.network.pojo;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


public class TumblrPost {

    public static final String REGULAR = "regular";
    public static final String LINK = "link";
    public static final String QUOTE = "quote";
    public static final String PHOTO = "photo";
    public static final String CONVERSATION = "conversation";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String ANSWER = "answer";

    @StringDef({REGULAR, LINK, QUOTE, PHOTO, CONVERSATION, VIDEO, AUDIO,ANSWER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PostType {}

    //METADATA
    private String postType;
    private String accountURL;
    private String creationTimestamp;
    private String userName;
    private String avatarURL;
    private String tumblrPostURL;
    private List<String> tagList;

    //Inner type references
    private Regular typeRegular;
    private Link typeLink;
    private Quote typeQuote;
    private Photo typePhoto;
    private Conversation typeConversation;
    private Video typeVideo;
    private Audio typeAudio;
    private Answer typeAnswer;

    public static class Regular{
        private String regularTitle;
        private String regularBody;

        public Regular(){}

        public Regular(String regularTitle, String regularBody) {
            this.regularTitle = regularTitle;
            this.regularBody = regularBody;
        }

        public String getRegularTitle() {
            return regularTitle;
        }

        public void setRegularTitle(String regularTitle) {
            this.regularTitle = regularTitle;
        }

        public String getRegularBody() {
            return regularBody;
        }

        public void setRegularBody(String regularBody) {
            this.regularBody = regularBody;
        }
    }

    public static class Link{
        private String linkTitle;
        private String linkDescription;
        private String linkURL;

        public Link(){}

        public Link(String linkTitle, String linkURL, String linkDescription) {
            this.linkTitle = linkTitle;
            this.linkDescription = linkDescription;
            this.linkURL = linkURL;
        }

        public String getLinkTitle() {
            return linkTitle;
        }

        public void setLinkTitle(String linkTitle) {
            this.linkTitle = linkTitle;
        }

        public String getLinkDescription() {
            return linkDescription;
        }

        public void setLinkDescription(String linkDescription) {
            this.linkDescription = linkDescription;
        }

        public String getLinkURL() {
            return linkURL;
        }

        public void setLinkURL(String linkURL) {
            this.linkURL = linkURL;
        }
    }

    public static class Quote{
        private String quoteText;
        private String quoteSource;

        public Quote(){}

        public Quote(String quoteText, String quoteSource) {
            this.quoteText = quoteText;
            this.quoteSource = quoteSource;
        }

        public String getQuoteText() {
            return quoteText;
        }

        public void setQuoteText(String quoteText) {
            this.quoteText = quoteText;
        }

        public String getQuoteSource() {
            return quoteSource;
        }

        public void setQuoteSource(String quoteSource) {
            this.quoteSource = quoteSource;
        }

    }

    public static class Photo{
        private String photoCaption;
        private String mainPhotoURL;
        private List<String> photoUrlList;

        public Photo(){}

        public Photo(String photoCaption, String mainPhotoURL, List<String> photoUrlList) {
            this.photoCaption = photoCaption;
            this.mainPhotoURL = mainPhotoURL;
            this.photoUrlList = photoUrlList;
        }

        public String getPhotoCaption() {
            return photoCaption;
        }

        public void setPhotoCaption(String photoCaption) {
            this.photoCaption = photoCaption;
        }

        public String getMainPhotoURL() {
            return mainPhotoURL;
        }

        public void setMainPhotoURL(String mainPhotoURL) {
            this.mainPhotoURL = mainPhotoURL;
        }


        public List<String> getPhotoUrlList() {
            return photoUrlList;
        }

        public void setPhotoUrlList(List<String> photoUrlList) {
            this.photoUrlList = photoUrlList;
        }

    }

    public static class Conversation{
        private List<String> conversationLineList;

        public Conversation(){}

        public Conversation(List<String> conversationLineList) {
            this.conversationLineList = conversationLineList;
        }

        public List<String> getConversationLineList() {
            return conversationLineList;
        }

        public void setConversationLineList(List<String> conversationLineList) {
            this.conversationLineList = conversationLineList;
        }
    }

    public static class Video{
        private String videoCaption;
        private String videoSourceURL;
        private String videoThumbnailURL;

        public Video(){}

        public Video(String videoCaption, String videoSourceURL, String videoThumbnailURL) {
            this.videoCaption = videoCaption;
            this.videoSourceURL = videoSourceURL;
            this.videoThumbnailURL = videoThumbnailURL;
        }

        public String getVideoThumbnailUrl() {
            return videoThumbnailURL;
        }

        public void setVideoThumbnailUrl(String videoThumbnail) {
            this.videoThumbnailURL = videoThumbnail;
        }

        public String getVideoCaption() {
            return videoCaption;
        }

        public void setVideoCaption(String videoCaption) {
            this.videoCaption = videoCaption;
        }

        public String getVideoSourceURL() {
            return videoSourceURL;
        }

        public void setVideoSourceURL(String videoSourceURL) {
            this.videoSourceURL = videoSourceURL;
        }

    }

    public static class Audio{
        private String audioCaption;
        private String audioURL;
        private String artist;
        private String album;
        private String year;
        private String track;
        private String title;

        public Audio(){}

        public Audio(String audioCaption, String audioURL, String artist, String album, String year, String track, String title) {
            this.audioCaption = audioCaption;
            this.audioURL = audioURL;
            this.artist = artist;
            this.album = album;
            this.year = year;
            this.track = track;
            this.title = title;
        }

        public String getAudioCaption() {
            return audioCaption;
        }

        public void setAudioCaption(String audioCaption) {
            this.audioCaption = audioCaption;
        }

        public String getAudioURL() {
            return audioURL;
        }

        public void setAudioURL(String audioURL) {
            this.audioURL = audioURL;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTrack() {
            return track;
        }

        public void setTrack(String track) {
            this.track = track;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Answer{
        //Not possible due to missing question origina
    }

    public TumblrPost(String tumblrPostURL, String creationTimestamp){
        this.tumblrPostURL = tumblrPostURL;
        this.creationTimestamp = creationTimestamp;
        tagList = new ArrayList<>();
    }

    public TumblrPost(String userName, String tumblrPostURL, String accountURL, String avatarURL, String creationTimestamp) {
        this.userName = userName;
        this.tumblrPostURL = tumblrPostURL;
        this.accountURL = accountURL;
        this.avatarURL = avatarURL;
        this.creationTimestamp = creationTimestamp;
        tagList = new ArrayList<>();
    }

    public void determinePostType(@PostType String postType){
        this.postType = postType;
    }

    public Regular getTypeRegular() {
        return typeRegular;
    }

    public void setTypeRegular(Regular typeRegular) {
        this.typeRegular = typeRegular;
    }

    public Link getTypeLink() {
        return typeLink;
    }

    public void setTypeLink(Link typeLink) {
        this.typeLink = typeLink;
    }

    public Quote getTypeQuote() {
        return typeQuote;
    }

    public void setTypeQuote(Quote typeQuote) {
        this.typeQuote = typeQuote;
    }

    public Photo getTypePhoto() {
        return typePhoto;
    }

    public void setTypePhoto(Photo typePhoto) {
        this.typePhoto = typePhoto;
    }

    public Conversation getTypeConversation() {
        return typeConversation;
    }

    public void setTypeConversation(Conversation typeConversation) {
        this.typeConversation = typeConversation;
    }

    public Video getTypeVideo() {
        return typeVideo;
    }

    public void setTypeVideo(Video typeVideo) {
        this.typeVideo = typeVideo;
    }

    public Audio getTypeAudio() {
        return typeAudio;
    }

    public void setTypeAudio(Audio typeAudio) {
        this.typeAudio = typeAudio;
    }

    public Answer getTypeAnswer() {
        return typeAnswer;
    }

    public void setTypeAnswer(Answer typeAnswer) {
        this.typeAnswer = typeAnswer;
    }

    public void addTag(String tag){
        tagList.add(tag);
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountURL() {
        return accountURL;
    }

    public void setAccountURL(String accountURL) {
        this.accountURL = accountURL;
    }

    public String getTumblrPostURL() {
        return tumblrPostURL;
    }

    public void setTumblrPostURL(String tumblrPostURL) {
        this.tumblrPostURL = tumblrPostURL;
    }

}
