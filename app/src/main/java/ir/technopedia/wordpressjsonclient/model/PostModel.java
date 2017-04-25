package ir.technopedia.wordpressjsonclient.model;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1 on 10/5/2016.
 */

public class PostModel extends SugarRecord {
    public int id, comment_count;
    public String url, title, content, date, author, comment_status, status, img;
    public List<String> categories;
    public List<CommentModel> comments;
    public JSONArray categoryjsonlist, commentjsonlist;

    public PostModel() {
        id = -1;
        url = title = content = date = author = comment_status = status = "";
        categories = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public void fromJson(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            url = jsonObject.getString("url");
            title = jsonObject.getString("title");
            status = jsonObject.getString("status");
            content = jsonObject.getString("content");
            date = jsonObject.getString("date");
            for (int i = 0; i < jsonObject.getJSONArray("categories").length(); i++) {
                categories.add(jsonObject.getJSONArray("categories").getJSONObject(i).getString("title"));
            }
            author = jsonObject.getJSONObject("author").getString("nickname");
            for (int i = 0; i < jsonObject.getJSONArray("comments").length(); i++) {
                CommentModel commentModel = new CommentModel();
                commentModel.fromJson(jsonObject.getJSONArray("comments").getJSONObject(i));
                comments.add(commentModel);
            }
            comment_count = jsonObject.getInt("comment_count");
            comment_status = jsonObject.getString("comment_status");
            img = jsonObject.getString("thumbnail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("url", url);
            jsonObject.put("title", title);
            jsonObject.put("status", status);
            jsonObject.put("content", content);
            jsonObject.put("date", date);

            categoryjsonlist = new JSONArray();
            for (int i = 0; i < categories.size(); i++) {
                categoryjsonlist.put(new JSONObject().put("title", categories.get(i)));
            }
            jsonObject.put("categories", categoryjsonlist);

            JSONObject authorobj = new JSONObject();
            authorobj.put("nickname", author);
            jsonObject.put("author", authorobj);

            commentjsonlist = new JSONArray();
            for (int i = 0; i < comments.size(); i++) {
                commentjsonlist.put(comments.get(i).toJson());
            }
            jsonObject.put("comments", commentjsonlist);

            jsonObject.put("comment_count", comment_count);
            jsonObject.put("comment_status", comment_status);
            jsonObject.put("thumbnail", img);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
