package ir.technopedia.wordpressjsonclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ir.technopedia.wordpressjsonclient.model.CommentModel;
import ir.technopedia.wordpressjsonclient.adapter.CommentAdapter;
import ir.technopedia.wordpressjsonclient.fragment.CommentSubmitDialog;
import ir.technopedia.wordpressjsonclient.view.SwipeBaseActivity;

public class CommentsActivity extends SwipeBaseActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<CommentModel> list;
    FloatingActionButton fab;
    CommentAdapter adapter;
    JSONArray jsonArray;
    int postId = -1;
    String commentStatus;
    CardView noDataCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.comment_list);
        fab = (FloatingActionButton) findViewById(R.id.fab_add_comment);
        noDataCard = (CardView) findViewById(R.id.no_data);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getResources().getString(R.string.comments));

        linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        list = new ArrayList<>();

        String data = getIntent().getStringExtra("data");
        postId = getIntent().getIntExtra("postId", -1);
        commentStatus = getIntent().getStringExtra("commentStatus");

        try {
            jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                CommentModel commentModel = new CommentModel();
                commentModel.fromJson(jsonArray.getJSONObject(i));
                list.add(commentModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter = new CommentAdapter(getBaseContext(), list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        if (!(list.size() > 0)) {
            recyclerView.setVisibility(View.GONE);
            noDataCard.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (commentStatus.equals("open")) {
                    CommentSubmitDialog commentSubmitDialog = new CommentSubmitDialog();
                    Bundle args = new Bundle();
                    args.putInt("postId", postId);
                    commentSubmitDialog.setArguments(args);
                    commentSubmitDialog.show(getSupportFragmentManager(), "");
                } else {
                    Snackbar.make(findViewById(android.R.id.content),
                            getResources().getString(R.string.comment_status_close_error),
                            Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
