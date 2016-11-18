package pl.edu.pwr.a200184student.my_personal_trainer.Adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter{

    private List<String> meals ;
    private HashMap<String,List<String>> mealItems;
    private Context context;

    public ExpandableListViewAdapter(Context context , List<String> meals , HashMap<String,List<String>> mealItems){
        this.meals = meals;
        this.mealItems = mealItems;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return meals.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mealItems.get(meals.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return meals.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mealItems.get(meals.get(groupPosition)).get(childPosition);
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
        String mealName= (String)this.getGroup(groupPosition);
        if(convertView != null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_expandable_view,null);
            TextView mealTextView = (TextView) convertView.findViewById(R.id.mealNameTextView);
            mealTextView.setTypeface(null,Typeface.BOLD);
            mealTextView.setText(mealName);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String mealItemName = (String) this.getChild(groupPosition,childPosition);
        if(convertView != null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_expandable_view,null);
            TextView mealTextView = (TextView) convertView.findViewById(R.id.mealItemTextView);
            mealTextView.setTypeface(null,Typeface.BOLD);
            mealTextView.setText(mealItemName);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
