package ir.technopedia.wordpressjsonclient.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.technopedia.wordpressjsonclient.R;

/**
 * Created by user1 on 10/7/2016.
 */

public class Util {

    // calling share intent
    public static void shareData(Context context, String title, String Body) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Body);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_with)));
    }

    public static void openAdress(Context context, String adress) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(adress));
        context.startActivity(intent);
    }

    // checking internet validation
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // saving data to sharedprefrences
    public static void saveData(Context context, String key, String text) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                "technopedia", context.MODE_PRIVATE).edit();
        editor.putString(key, text);
        editor.commit();
    }

    // loading data from sharedprefrences
    public static String loadData(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                "technopedia", context.MODE_PRIVATE);
        String text = prefs.getString(key, "");
        return text;
    }

    // validating email id
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
