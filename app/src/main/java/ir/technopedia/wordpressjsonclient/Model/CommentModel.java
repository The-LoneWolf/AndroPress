package ir.technopedia.wordpressjsonclient.model;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user1 on 10/5/2016.
 */

public class CommentModel extends SugarRecord {
    public int id;
    public String name, date, content;

    public CommentModel() {
        id = -1;
        name = date = content = "";
    }

    public void fromJson(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            name = jsonObject.getString("name");
            date = jsonObject.getString("date");
            content = jsonObject.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("date", date);
            jsonObject.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
