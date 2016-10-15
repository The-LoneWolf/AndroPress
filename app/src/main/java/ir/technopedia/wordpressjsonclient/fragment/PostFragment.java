package ir.technopedia.wordpressjsonclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import ir.technopedia.wordpressjsonclient.DetailActivity;
import ir.technopedia.wordpressjsonclient.Model.PostModel;
import ir.technopedia.wordpressjsonclient.R;
import ir.technopedia.wordpressjsonclient.adapter.PostAdapter;
import ir.technopedia.wordpressjsonclient.util.NetUtil;
import ir.technopedia.wordpressjsonclient.util.RecyclerItemClickListener;
import ir.technopedia.wordpressjsonclient.util.Util;

public class PostFragment extends Fragment {

    String adress;
    RecyclerView postList;
    List<PostModel> postArray;
    PostAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    CardView noDataCard;
    Button btnReload;
    int page = 1, selectedCat = 0, previousTotal = 0,
            visibleThreshold = 5, firstVisibleItem, visibleItemCount,
            totalItemCount;
    boolean loading = true;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        postList = (RecyclerView) rootView.findViewById(R.id.post_list);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        btnReload = (Button) rootView.findViewById(R.id.btn_reload);
        noDataCard = (CardView) rootView.findViewById(R.id.no_data);

        postArray = new ArrayList<>();
        adapter = new PostAdapter(getActivity(), postArray);
        postList.setLayoutManager(linearLayoutManager);
        postList.setAdapter(adapter);
        refreshPosts(selectedCat);

        postList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("data", postArray.get(position).toJson().toString());
                    startActivity(intent);
                }
            }
        }));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPosts(selectedCat);
            }
        });

        postList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    page++;
                    getPosts(selectedCat);
                    loading = true;
                }

            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                noDataCard.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(true);
                refreshPosts(selectedCat);
            }
        });

        return rootView;
    }

    public void refreshPosts(int cat) {
        selectedCat = cat;
        swipeRefreshLayout.setRefreshing(true);
        postArray = new ArrayList<>();
        page = 1;
        getPosts(cat);
    }

    public void getPosts(int cat) {
        if (cat == 0) {
            adress = "get_recent_posts/?page=" + page + "&count=" + NetUtil.postCount;
        } else {
            adress = "get_category_posts/?id=" + cat + "&count=" + NetUtil.postCount + "&page=" + page;
        }

        if (Util.isNetworkAvailable(getActivity())) {
            NetUtil.get(adress, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray posts = response.getJSONArray("posts");

                        PostModel postModel;
                        for (int i = 0; i < posts.length(); i++) {
                            postModel = new PostModel();
                            postModel.fromJson(posts.getJSONObject(i));
                            postArray.add(postModel);
                        }
                        adapter.update(postArray);
                        swipeRefreshLayout.setRefreshing(false);

                        if (!(postArray.size() > 0)) {
                            swipeRefreshLayout.setVisibility(View.GONE);
                            noDataCard.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    swipeRefreshLayout.setRefreshing(false);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    noDataCard.setVisibility(View.VISIBLE);
                }

            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setVisibility(View.GONE);
            noDataCard.setVisibility(View.VISIBLE);
        }
    }

}
