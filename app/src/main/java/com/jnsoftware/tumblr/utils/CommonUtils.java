package com.jnsoftware.tumblr.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jnsoftware.tumblr.R;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public class CommonUtils {
    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String extractImage (String text)
    {
        String validUrl = "";

        String urlRegex = "(http)?s?:?(\\/\\/[^\"']*\\.(?:png|jpg|jpeg|gif|png|svg))";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        if(urlMatcher.find()) {
            validUrl = text.substring(urlMatcher.start(0),
                    urlMatcher.end(0));
        }

        return validUrl;
    }

    public static String extractVideoUrl (String text)
    {
        String validUrl = "";

        String urlRegex = "(http)?s?:?\\/\\/[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(\\/\\S*)?mp4";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);


        if(urlMatcher.find()) {
            validUrl = text.substring(urlMatcher.start(0),
                    urlMatcher.end(0));
        }

        return validUrl;
    }

    public static String extractAudioUrl (String text)
    {
        String validUrl = "";

        String urlRegex = "(http)?s?:?\\/\\/[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(\\/\\S*)?mp3";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);


        if(urlMatcher.find()) {
            validUrl = text.substring(urlMatcher.start(0),
                    urlMatcher.end(0));
        }

        return validUrl;
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
