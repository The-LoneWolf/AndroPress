package ir.technopedia.wordpressjsonclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ir.technopedia.wordpressjsonclient.model.CategoryModel;
import ir.technopedia.wordpressjsonclient.model.ExpandedMenuModel;
import ir.technopedia.wordpressjsonclient.R;


public class NavExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ExpandedMenuModel> mListDataHeader; // header titles

    private HashMap<ExpandedMenuModel, List<CategoryModel>> mListDataChild;

    public NavExpandableListAdapter(Context context, List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<CategoryModel>> listChildData) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<CategoryModel> list = this.mListDataChild.get(this.mListDataHeader.get(groupPosition));
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition).title;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.navigation_list_header, null);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.submenu);
        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.icon);
        ImageView indicatorIcon = (ImageView) convertView.findViewById(R.id.indicator);
        lblListHeader.setText(headerTitle.iconName);
        headerIcon.setImageResource(headerTitle.iconImg);
        if (getChildrenCount(groupPosition) > 0) {
            indicatorIcon.setVisibility(View.VISIBLE);
            if (mListDataHeader.get(groupPosition).is_expanded) {
                indicatorIcon.setImageResource(R.drawable.ic_expand_yes);
            } else {
                indicatorIcon.setImageResource(R.drawable.ic_expand_no);
            }
        } else {
            indicatorIcon.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.navigation_list_menu, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);
        txtListChild.setText(childText);
        if (mListDataHeader.get(groupPosition).is_expanded) {

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updated() {
        this.notifyDataSetChanged();
    }

}