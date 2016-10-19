package ir.technopedia.wordpressjsonclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import ir.technopedia.wordpressjsonclient.R;
import ir.technopedia.wordpressjsonclient.util.NetUtil;
import ir.technopedia.wordpressjsonclient.util.Util;

/**
 * Created by user1 on 10/7/2016.
 */

public class CommentSubmitDialog extends DialogFragment {

    EditText edtName, edtEmail, edtContent;
    Button btnSubmit;
    int postId = -1;
    String name, email, content;
    View mView;
    LinearLayout layoutInputs, layoutProgress;
    TextInputLayout txtInputEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_comment_submit, container, false);
        getDialog().setTitle(getResources().getString(R.string.add_comment));

        layoutInputs = (LinearLayout) mView.findViewById(R.id.dialog_inputs);
        layoutProgress = (LinearLayout) mView.findViewById(R.id.dialog_progress);
        edtName = (EditText) mView.findViewById(R.id.input_name);
        edtEmail = (EditText) mView.findViewById(R.id.input_email);
        txtInputEmail = (TextInputLayout) mView.findViewById(R.id.input_layout_email);
        edtContent = (EditText) mView.findViewById(R.id.input_content);
        btnSubmit = (Button) mView.findViewById(R.id.btn_submit);
        layoutInputs.setVisibility(View.VISIBLE);
        layoutProgress.setVisibility(View.GONE);

        postId = getArguments().getInt("postId");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment();
            }
        });

        if (!Util.loadData(getActivity(), "comment_name").equals("")) {
            edtName.setText(Util.loadData(getActivity(), "comment_name"));
        }

        if (!Util.loadData(getActivity(), "comment_email").equals("")) {
            edtEmail.setText(Util.loadData(getActivity(), "comment_email"));
        }

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Util.isValidEmail(edtEmail.getText().toString())) {
                    txtInputEmail.setError(getString(R.string.invalid_email));
                } else {
                    txtInputEmail.setErrorEnabled(false);
                }
            }
        });

        return mView;
    }

    public void submitComment() {

        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        content = edtContent.getText().toString();
        String url = "respond/submit_comment/?post_id=" + postId + "&name=" + name + "&email=" + email + "&content=" + content;

        if (Util.isNetworkAvailable(getActivity())) {

            layoutInputs.setVisibility(View.GONE);
            layoutProgress.setVisibility(View.VISIBLE);
            Util.saveData(getActivity(), "comment_name", name);
            Util.saveData(getActivity(), "comment_email", email);

            NetUtil.get(url, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    edtContent.setText("");
//                    Snackbar.make(getActivity().findViewById(android.R.id.content),
//                            getResources().getString(R.string.comment_submit_success),
//                            Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),getResources().getString(R.string.comment_submit_success),Toast.LENGTH_LONG).show();
                    getDialog().dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    Snackbar.make(getActivity().findViewById(android.R.id.content),
//                            getResources().getString(R.string.comment_status_close_error),
//                            Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),getResources().getString(R.string.comment_status_close_error),Toast.LENGTH_LONG).show();
                    getDialog().dismiss();
                }
            });
        } else {
//            Snackbar.make(getActivity().findViewById(android.R.id.content),
//                    getResources().getString(R.string.no_internet),
//                    Snackbar.LENGTH_SHORT).show();
            Toast.makeText(getActivity(),getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
        }
    }
}