package com.jnsoftware.tumblr.ui.main;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.indicators.PagerIndicator;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.jnsoftware.tumblr.R;
import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;
import com.jnsoftware.tumblr.ui.base.BaseViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import xyz.schwaab.avvylib.AvatarView;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public class TumblrFeedAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    OnBottomReachedListener onBottomReachedListener;

    private static final String TAG = "TumblrFeedAdapter";
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_REGULAR = 1;
    public static final int VIEW_TYPE_LINK = 2;
    public static final int VIEW_TYPE_QUOTE = 3;
    public static final int VIEW_TYPE_PHOTO = 4;
    public static final int VIEW_TYPE_CONVERSATION = 5;
    public static final int VIEW_TYPE_VIDEO = 6;
    public static final int VIEW_TYPE_AUDIO = 7;

    private Callback mCallback;
    private List<TumblrPost> mTumblrPostList;
    final public Context mContext;

    public TumblrFeedAdapter(List<TumblrPost> tumblrPostList, Context mContext) {
        mTumblrPostList = tumblrPostList;
        this.mContext = mContext;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if ((position == mTumblrPostList.size() - 1) && (mTumblrPostList.get(0).getPostCount() > mTumblrPostList.size())) {
            onBottomReachedListener.onBottomReached(position + 1 );
        }
        switch (getItemViewType(position)) {
            case VIEW_TYPE_REGULAR:
                ((RegularViewHolder) holder).setRegularDetails(mTumblrPostList.get(position));
                break;
            case VIEW_TYPE_LINK:
                ((LinkViewHolder) holder).setLinkDetails(mTumblrPostList.get(position));
                break;
            case VIEW_TYPE_QUOTE:
                ((QuoteViewHolder) holder).setQuoteDetails(mTumblrPostList.get(position));
                break;
            case VIEW_TYPE_PHOTO:
                ((PhotoViewHolder) holder).setPhotoDetails(mTumblrPostList.get(position));
                break;
            case VIEW_TYPE_CONVERSATION:
                ((ConversationViewHolder) holder).setConversationDetails(mTumblrPostList.get(position));
                break;
            case VIEW_TYPE_VIDEO:
                ((VideoViewHolder) holder).setVideoDetails(mTumblrPostList.get(position));
                break;
            case VIEW_TYPE_AUDIO:
                ((AudioViewHolder) holder).setAudioDetails(mTumblrPostList.get(position));
                break;
            case VIEW_TYPE_EMPTY:
            default:
                ((EmptyViewHolder) holder).setEmptyDetails();
                break;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_REGULAR:
                return new RegularViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_regular, parent, false));
            case VIEW_TYPE_LINK:
                return new LinkViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false));
            case VIEW_TYPE_QUOTE:
                return new QuoteViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote, parent, false));
            case VIEW_TYPE_PHOTO:
                return new PhotoViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
            case VIEW_TYPE_CONVERSATION:
                return new ConversationViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false));
            case VIEW_TYPE_VIDEO:
                return new VideoViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
            case VIEW_TYPE_AUDIO:
                return new AudioViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTumblrPostList != null && mTumblrPostList.size() > 0) {
            return mTumblrPostList.get(position).getPostType();
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mTumblrPostList != null && mTumblrPostList.size() > 0) {
            return mTumblrPostList.size();
        } else {
            return 1;
        }
    }

    public void updateItems(List<TumblrPost> postList) {
        mTumblrPostList.addAll(postList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    private void clearAdapterData(){
        mTumblrPostList.clear();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public class EmptyViewHolder extends BaseViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }

        public void setEmptyDetails(){};

    }

    public class RegularViewHolder extends BaseViewHolder {

        AvatarView userAvatar;
        TextView userName;
        LinearLayout userInfo;
        FlexboxLayout tagContainer;

        TextView title;
        TextView body;


        public RegularViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            userInfo = itemView.findViewById(R.id.userInfo);
            tagContainer = itemView.findViewById(R.id.tagsLayout);

            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);

        }

        protected void clear() {
            userAvatar.setImageDrawable(null);
            userName.setText("");
            tagContainer.removeAllViews();
            title.setText("");
            body.setText("");
        }

        private void setRegularDetails(TumblrPost tumblrPost){

            clear();

            tagContainer.setFlexDirection(FlexDirection.ROW);

            if (tumblrPost.getAvatarURL() != null) {
                Glide.with(itemView.getContext())
                        .load(tumblrPost.getAvatarURL())
                        .into(userAvatar);
            }

            if (tumblrPost.getUserName() != null) {
                userName.setText(tumblrPost.getUserName());
            }

            if(tumblrPost.getTypeRegular().getRegularTitle() !=null ){
                title.setText(tumblrPost.getTypeRegular().getRegularTitle());
            }

            if(tumblrPost.getTypeRegular().getRegularBody() !=null ){
                body.setText(tumblrPost.getTypeRegular().getRegularBody());
            }

            if(tumblrPost.getTagList() != null){

                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for ( String tag : tumblrPost.getTagList()) {
                    TextView tagView = new TextView(itemView.getContext());
                    tagView.setText("#" + tag);
                    layoutParams.setMargins(10, 5, 10, 5);
                    tagView.setPadding(17, 15, 17, 15);
                    tagContainer.addView(tagView, layoutParams);
                }
            }

            userInfo.setOnClickListener(v -> {
                if (tumblrPost.getAccountURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getAccountURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: account url is not correct");
                    }
                }
            });
        }
    }

    public class LinkViewHolder extends BaseViewHolder {

        AvatarView userAvatar;
        TextView userName;
        LinearLayout userInfo;
        FlexboxLayout tagContainer;

        TextView link;
        TextView linkDescription;



        public LinkViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            userInfo = itemView.findViewById(R.id.userInfo);
            tagContainer = itemView.findViewById(R.id.tagsLayout);

            link = itemView.findViewById(R.id.link);
            linkDescription = itemView.findViewById(R.id.linkDescription);

        }

        protected void clear() {
            userAvatar.setImageDrawable(null);
            userName.setText("");
            tagContainer.removeAllViews();
            link.setText("");
            linkDescription.setText("");
        }

        public void setLinkDetails(TumblrPost tumblrPost) {

            tagContainer.setFlexDirection(FlexDirection.ROW);

            clear();

            if (tumblrPost.getAvatarURL() != null) {
                Glide.with(itemView.getContext())
                        .load(tumblrPost.getAvatarURL())
                        .into(userAvatar);
            }

            if (tumblrPost.getUserName() != null) {
                userName.setText(tumblrPost.getUserName());
            }

            if(tumblrPost.getTypeLink().getLinkTitle() != null){
                link.setText(tumblrPost.getTypeLink().getLinkTitle());
            }

            if(tumblrPost.getTypeLink().getLinkDescription() != null){
                linkDescription.setText(tumblrPost.getTypeLink().getLinkDescription());
            }

            if(tumblrPost.getTagList() != null){

                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for ( String tag : tumblrPost.getTagList()) {
                    TextView tagView = new TextView(itemView.getContext());
                    tagView.setText("#" + tag);
                    layoutParams.setMargins(10, 5, 10, 5);
                    tagView.setPadding(17, 15, 17, 15);
                    tagContainer.addView(tagView, layoutParams);
                }
            }

            link.setOnClickListener( v -> {
                if(tumblrPost.getTypeLink().getLinkURL() != null ){
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getTypeLink().getLinkURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e("LINK", "onClick: url is not correct");
                    }
                }
            });


            userInfo.setOnClickListener(v -> {
                if (tumblrPost.getAccountURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getAccountURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e("LINK ACC", "onClick: account url is not correct");
                    }
                }
            });
        }
    }

    public class QuoteViewHolder extends BaseViewHolder {

        AvatarView userAvatar;
        TextView userName;
        LinearLayout userInfo;
        FlexboxLayout tagContainer;

        TextView quoteText;
        TextView quoteSource;


        public QuoteViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            userInfo = itemView.findViewById(R.id.userInfo);
            tagContainer = itemView.findViewById(R.id.tagsLayout);

            quoteText = itemView.findViewById(R.id.quoteText);
            quoteSource = itemView.findViewById(R.id.quoteSource);

        }

        protected void clear() {
            userAvatar.setImageDrawable(null);
            userName.setText("");
            tagContainer.removeAllViews();
            quoteSource.setText("");
            quoteText.setText("");
        }

        public void setQuoteDetails(TumblrPost tumblrPost) {

            tagContainer.setFlexDirection(FlexDirection.ROW);

            clear();

            if (tumblrPost.getAvatarURL() != null) {
                Glide.with(itemView.getContext())
                        .load(tumblrPost.getAvatarURL())
                        .into(userAvatar);
            }

            if (tumblrPost.getUserName() != null) {
                userName.setText(tumblrPost.getUserName());
            }

            if(tumblrPost.getTypeQuote().getQuoteText() !=null ){
                quoteText.setText(tumblrPost.getTypeQuote().getQuoteText());
            }

            if(tumblrPost.getTypeQuote().getQuoteSource() !=null ){
                quoteSource.setText(tumblrPost.getTypeQuote().getQuoteSource());
            }

            if(tumblrPost.getTagList() != null){

                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for ( String tag : tumblrPost.getTagList()) {
                    TextView tagView = new TextView(itemView.getContext());
                    tagView.setText("#" + tag);
                    layoutParams.setMargins(10, 5, 10, 5);
                    tagView.setPadding(17, 15, 17, 15);
                    tagContainer.addView(tagView, layoutParams);
                }
            }

            userInfo.setOnClickListener(v -> {
                if (tumblrPost.getAccountURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getAccountURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: account url is not correct");
                    }
                }
            });
        }
    }

    public class PhotoViewHolder extends BaseViewHolder {

        AvatarView userAvatar;
        TextView userName;
        LinearLayout userInfo;
        FlexboxLayout tagContainer;

        TextView caption;
        SliderLayout mPhotoSlider;

        List<String> photoUrlList;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            userInfo = itemView.findViewById(R.id.userInfo);
            tagContainer = itemView.findViewById(R.id.tagsLayout);

            caption = itemView.findViewById(R.id.caption);
            mPhotoSlider = itemView.findViewById(R.id.photoSlider);

            photoUrlList = new ArrayList<>();

        }

        protected void clear() {
            userAvatar.setImageDrawable(null);
            userName.setText("");
            photoUrlList.clear();
            tagContainer.removeAllViews();
            mPhotoSlider.removeAllSliders();
        }

        public void setPhotoDetails(TumblrPost tumblrPost) {

            tagContainer.setFlexDirection(FlexDirection.ROW);

            clear();

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.centerCrop();

            if (tumblrPost.getAvatarURL() != null) {
                Glide.with(itemView.getContext())
                        .load(tumblrPost.getAvatarURL())
                        .into(userAvatar);
            }

            if (tumblrPost.getUserName() != null) {
                userName.setText(tumblrPost.getUserName());
            }

            if(tumblrPost.getTypePhoto().getPhotoCaption() != null){
                caption.setText(tumblrPost.getTypePhoto().getPhotoCaption());
            }

            if(tumblrPost.getTypePhoto().getPhotoUrlList() != null){
                photoUrlList = new ArrayList<>();
                photoUrlList.add(tumblrPost.getTypePhoto().getMainPhotoURL());
                photoUrlList.addAll(tumblrPost.getTypePhoto().getPhotoUrlList());
            } else {
                photoUrlList = new ArrayList<>();
                photoUrlList.add(tumblrPost.getTypePhoto().getMainPhotoURL());
            }

            for (String photoUrl : photoUrlList) {
                DefaultSliderView sliderView = new DefaultSliderView(itemView.getContext());
                // initialize SliderLayout
                sliderView
                        .image(photoUrl)
                        .setRequestOption(requestOptions)
                        .setProgressBarVisible(true);
                mPhotoSlider.addSlider(sliderView);
            }

            if(tumblrPost.getTagList() != null){

                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for ( String tag : tumblrPost.getTagList()) {
                    TextView tagView = new TextView(itemView.getContext());
                    tagView.setText("#" + tag);
                    layoutParams.setMargins(10, 5, 10, 5);
                    tagView.setPadding(17, 15, 17, 15);
                    tagContainer.addView(tagView, layoutParams);
                }
            }

            userInfo.setOnClickListener(v -> {
                if (tumblrPost.getAccountURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getAccountURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: account url is not correct");
                    }
                }
            });
        }
    }

    public class ConversationViewHolder extends BaseViewHolder {


        AvatarView userAvatar;
        TextView userName;
        LinearLayout userInfo;
        FlexboxLayout tagContainer;

        LinearLayout conversationContainer;


        public ConversationViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            userInfo = itemView.findViewById(R.id.userInfo);
            tagContainer = itemView.findViewById(R.id.tagsLayout);

            conversationContainer = itemView.findViewById(R.id.conversationContainer);

        }

        protected void clear() {
            userAvatar.setImageDrawable(null);
            userName.setText("");
            tagContainer.removeAllViews();
            conversationContainer.removeAllViews();
        }

        public void setConversationDetails(TumblrPost tumblrPost) {

            tagContainer.setFlexDirection(FlexDirection.ROW);

            clear();

            if (tumblrPost.getAvatarURL() != null) {
                Glide.with(itemView.getContext())
                        .load(tumblrPost.getAvatarURL())
                        .into(userAvatar);
            }

            if (tumblrPost.getUserName() != null) {
                userName.setText(tumblrPost.getUserName());
            }

            if(tumblrPost.getTypeConversation().getConversationLineList() != null){

                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for ( String line : tumblrPost.getTypeConversation().getConversationLineList()) {
                    TextView conversationLineView = new TextView(itemView.getContext());
                    conversationLineView.setText(line);
                    layoutParams.setMargins(10, 3, 10, 3);
                    conversationLineView.setPadding(5, 5, 5, 5);
                    conversationLineView.setTextColor(mContext.getResources().getColor(R.color.text_grey));
                    conversationContainer.addView(conversationLineView, layoutParams);
                }
            }

            if(tumblrPost.getTagList() != null){

                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for ( String tag : tumblrPost.getTagList()) {
                    TextView tagView = new TextView(itemView.getContext());
                    tagView.setText("#" + tag);
                    layoutParams.setMargins(10, 5, 10, 5);
                    tagView.setPadding(17, 15, 17, 15);
                    tagContainer.addView(tagView, layoutParams);
                }
            }

            userInfo.setOnClickListener(v -> {
                if (tumblrPost.getAccountURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getAccountURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: account url is not correct");
                    }
                }
            });
        }
    }

    public class VideoViewHolder extends BaseViewHolder {

        AvatarView userAvatar;
        TextView userName;
        LinearLayout userInfo;
        FlexboxLayout tagContainer;

        TextView caption;
        ImageView videoThumbnail;
        ImageView playVideoButton;


        public VideoViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            userInfo = itemView.findViewById(R.id.userInfo);
            tagContainer = itemView.findViewById(R.id.tagsLayout);

            caption = itemView.findViewById(R.id.caption);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            playVideoButton = itemView.findViewById(R.id.playVideoButton);
        }

        protected void clear() {
            userAvatar.setImageDrawable(null);
            userName.setText("");
            tagContainer.removeAllViews();

            caption.setText("");
            videoThumbnail.setImageDrawable(null);
        }


        public void setVideoDetails(TumblrPost tumblrPost) {

            tagContainer.setFlexDirection(FlexDirection.ROW);

            clear();

            if (tumblrPost.getAvatarURL() != null) {
                Glide.with(itemView.getContext())
                        .load(tumblrPost.getAvatarURL())
                        .into(userAvatar);
            }

            if (tumblrPost.getUserName() != null) {
                userName.setText(tumblrPost.getUserName());
            }

            if (tumblrPost.getTypeVideo().getVideoCaption() != null){
                caption.setText(tumblrPost.getTypeVideo().getVideoCaption());
            }

            if (tumblrPost.getTypeVideo().getVideoThumbnailUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(tumblrPost.getTypeVideo().getVideoThumbnailUrl())
                        .into(videoThumbnail);
            }

            playVideoButton.setOnClickListener(v -> {
                if (tumblrPost.getTypeVideo().getVideoSourceURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getTypeVideo().getVideoSourceURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: video url is not correct");
                    }
                }
            });


            if(tumblrPost.getTagList() != null){

                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for ( String tag : tumblrPost.getTagList()) {
                    TextView tagView = new TextView(itemView.getContext());
                    tagView.setText("#" + tag);
                    layoutParams.setMargins(10, 5, 10, 5);
                    tagView.setPadding(17, 15, 17, 15);
                    tagContainer.addView(tagView, layoutParams);
                }
            }

            userInfo.setOnClickListener(v -> {
                if (tumblrPost.getAccountURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getAccountURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: account url is not correct");
                    }
                }
            });
        }
    }

    public class AudioViewHolder extends BaseViewHolder {

        AvatarView userAvatar;
        TextView userName;
        LinearLayout userInfo;
        FlexboxLayout tagContainer;

        TextView caption;

        Button listenButton;

        TextView title;
        TextView artist;
        TextView album;
        TextView year;
        TextView track;


        public AudioViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            userName = itemView.findViewById(R.id.userName);
            userInfo = itemView.findViewById(R.id.userInfo);
            tagContainer = itemView.findViewById(R.id.tagsLayout);

            caption = itemView.findViewById(R.id.caption);

            listenButton = itemView.findViewById(R.id.listenButton);

            title = itemView.findViewById(R.id.title);
            artist = itemView.findViewById(R.id.artist);
            album = itemView.findViewById(R.id.album);
            year = itemView.findViewById(R.id.year);
            track = itemView.findViewById(R.id.track);
        }

        protected void clear() {
            userAvatar.setImageDrawable(null);
            userName.setText("");
            tagContainer.removeAllViews();
            caption.setText("");
            title.setText("");
            artist.setText("");
            album.setText("");
            caption.setText("");
            year.setText("");
        }


        public void setAudioDetails(TumblrPost tumblrPost) {

            tagContainer.setFlexDirection(FlexDirection.ROW);

            clear();

            if (tumblrPost.getAvatarURL() != null) {
                Glide.with(itemView.getContext())
                        .load(tumblrPost.getAvatarURL())
                        .into(userAvatar);
            }

            if (tumblrPost.getUserName() != null) {
                userName.setText(tumblrPost.getUserName());
            }

            if (tumblrPost.getTypeAudio().getAudioCaption() != null){
                caption.setText(tumblrPost.getTypeAudio().getAudioCaption());
            }

            if (tumblrPost.getTypeAudio().getTitle() != null){
                title.setText(tumblrPost.getTypeAudio().getTitle());
            }

            if (tumblrPost.getTypeAudio().getArtist() != null){
                artist.setText(tumblrPost.getTypeAudio().getArtist());
            }

            if (tumblrPost.getTypeAudio().getAlbum() != null){
                album.setText(tumblrPost.getTypeAudio().getAlbum());
            }

            if (tumblrPost.getTypeAudio().getYear() != null){
                year.setText(tumblrPost.getTypeAudio().getYear());
            }

            if (tumblrPost.getTypeAudio().getTrack() != null){
                track.setText(tumblrPost.getTypeAudio().getTrack());
            }

            listenButton.setOnClickListener(v -> {
                if (tumblrPost.getTypeAudio().getAudioURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getTypeAudio().getAudioURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: audio url is not correct");
                    }
                }
            });


            if(tumblrPost.getTagList() != null){

                LinearLayout.LayoutParams layoutParams = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for ( String tag : tumblrPost.getTagList()) {
                    TextView tagView = new TextView(itemView.getContext());
                    tagView.setText("#" + tag);
                    layoutParams.setMargins(10, 5, 10, 5);
                    tagView.setPadding(17, 15, 17, 15);
                    tagContainer.addView(tagView, layoutParams);
                }
            }

            userInfo.setOnClickListener(v -> {
                if (tumblrPost.getAccountURL() != null) {
                    try {
                        Intent browserIntent = new
                                Intent(Intent.ACTION_VIEW, Uri.parse(tumblrPost.getAccountURL()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: account url is not correct");
                    }
                }
            });
        }
    }

}
