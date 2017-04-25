package ir.technopedia.wordpressjsonclient.model;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import ir.technopedia.wordpressjsonclient.util.NetUtil;

public class CategoryModel extends SugarRecord {

    public String title;
    public int id, postCount;

    public String getUrl(int page) {
        return "get_category_posts/?id=" + id + "&count=" + NetUtil.postCount + "&page=" + page;
    }

    public void fromJson(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.title = jsonObject.getString("title");
            this.postCount = jsonObject.getInt("post_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("title", this.title);
            jsonObject.put("post_count", this.postCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
