package ir.technopedia.wordpressjsonclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import ir.technopedia.wordpressjsonclient.R;
import ir.technopedia.wordpressjsonclient.util.NetUtil;

/**
 * Created by user1 on 10/7/2016.
 */

public class CommentSubmitDialog extends DialogFragment {

    EditText edtName, edtEmail, edtContent;
    Button btnSubmit;
    int postId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.dialog_comment_submit, container, false);
        getDialog().setTitle(getResources().getString(R.string.add_comment));

        edtName = (EditText) mView.findViewById(R.id.input_name);
        edtEmail = (EditText) mView.findViewById(R.id.input_email);
        edtContent = (EditText) mView.findViewById(R.id.input_content);
        btnSubmit = (Button) mView.findViewById(R.id.btn_submit);

        postId = getArguments().getInt("postId");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment();
            }
        });

        return mView;
    }

    public void submitComment() {

        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String content = edtContent.getText().toString();
        String url = "respond/submit_comment/?post_id=" + postId + "&name=" + name + "&email=" + email + "&content=" + content;

        NetUtil.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                edtName.setText("");
                edtEmail.setText("");
                edtContent.setText("");
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getResources().getString(R.string.comment_submit_success),
                        Snackbar.LENGTH_SHORT).show();
                getDialog().dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getResources().getString(R.string.comment_status_close_error),
                        Snackbar.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
    }
}