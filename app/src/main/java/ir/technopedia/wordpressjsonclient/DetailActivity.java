package ir.technopedia.wordpressjsonclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.technopedia.wordpressjsonclient.Model.PostModel;
import ir.technopedia.wordpressjsonclient.util.Util;
import ir.technopedia.wordpressjsonclient.view.BadgeDrawable;
import ir.technopedia.wordpressjsonclient.view.SwipeBaseActivity;

public class DetailActivity extends SwipeBaseActivity {

    PostModel postModel;
    WebView webView;
    ImageView imageView;
    TextView txtAuthorDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.web);
        imageView = (ImageView) findViewById(R.id.image);
        txtAuthorDate = (TextView) findViewById(R.id.txt_date_author);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent bundle = getIntent();
        postModel = new PostModel();
        try {
            postModel.fromJson(new JSONObject(bundle.getStringExtra("data")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setTitle(postModel.title);

        String html = htmlifyText(postModel.content);
        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);

        Picasso.with(getBaseContext())
                .load(postModel.img)
                .placeholder(R.drawable.placeholder)
                .into(imageView);

        txtAuthorDate.setText(
                Html.fromHtml(getString(R.string.written_by) + "<b> " + postModel.author +
                        " </b> " + getString(R.string.on_date) + " <b> " + postModel.date + "</b>")
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem itemComment = menu.findItem(R.id.action_comments);
        LayerDrawable icon = (LayerDrawable) itemComment.getIcon();
        setBadgeCount(this, icon, postModel.comment_count + "");
        if (!(postModel.comment_status.equals("open")) && !(postModel.comment_count > 0)) {
            itemComment.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_share) {
            String body = String.format(getResources().getString(R.string.share_body), postModel.title, postModel.url);
            Util.shareData(DetailActivity.this, getResources().getString(R.string.share_title), body);
        } else if (id == R.id.action_comments) {
            Intent intent = new Intent(getBaseContext(), CommentsActivity.class);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < postModel.comments.size(); i++) {
                jsonArray.put(postModel.comments.get(i).toJson());
            }
            intent.putExtra("data", jsonArray.toString());
            intent.putExtra("postId", postModel.id);
            intent.putExtra("commentStatus", postModel.comment_status);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public String htmlifyText(String txt) {

        String html = "";
        html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>\n" +
                "<style> \n" +
                "img{display: inline; max-width:100%; width:auto; height:auto;}" +
                "table{border-collapse: collapse !important;width:auto !important; }" +
                "table, th, td {\n" +
                "    border: 1px solid black; width:100% !important; \n" +
                "}" +
                "body{text-align:justify;color:#212121 !important;}" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"\">"
                + txt + "</body></html>";
        return html;

    }
}
