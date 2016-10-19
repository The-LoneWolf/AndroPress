package ir.technopedia.wordpressjsonclient.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.technopedia.wordpressjsonclient.model.CommentModel;
import ir.technopedia.wordpressjsonclient.R;

/**
 * Created by user1 on 10/7/2016.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.DataObjectHolder> {

    List<CommentModel> mDataset;
    Context context;

    public CommentAdapter(Context context, List<CommentModel> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public CommentAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_comments, parent, false);
        CommentAdapter.DataObjectHolder dataObjectHolder = new CommentAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.DataObjectHolder holder, int position) {
        holder.title.setText(mDataset.get(position).name + " on : " + mDataset.get(position).date);
        holder.content.setText(Html.fromHtml(mDataset.get(position).content));
    }

    public void addItem(CommentModel dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void update(List<CommentModel> list) {
        mDataset = list;
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView title, content;

        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}