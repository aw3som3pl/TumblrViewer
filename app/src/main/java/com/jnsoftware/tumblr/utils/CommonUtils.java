package com.jnsoftware.tumblr.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

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

    public static List<String> extractUrls (String text)
    {
        List<String> validUrls = new ArrayList<>();

        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            validUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return validUrls;
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
