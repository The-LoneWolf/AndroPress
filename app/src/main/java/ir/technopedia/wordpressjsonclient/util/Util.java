package ir.technopedia.wordpressjsonclient.util;

import android.content.Context;
import android.content.Intent;

import ir.technopedia.wordpressjsonclient.R;

/**
 * Created by user1 on 10/7/2016.
 */

public class Util {

    public static void shareData(Context context, String title, String Body) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Body);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_with)));
    }
}
