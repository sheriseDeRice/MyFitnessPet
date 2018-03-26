package com.example.sherisesinyeelam.seprojectbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michal on 25.03.2018.
 */

public class ListAdapter extends ArrayAdapter<Entry> {

    protected LayoutInflater inflater;
    protected  ArrayList<Entry> entries;
    protected  int resourceID;

    public ListAdapter(Context context, int resource, ArrayList<Entry> entries) {
        super(context, resource, entries);
        this.entries = entries;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resourceID = resource;

    }

    //ToDo change names so they will make sense
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = inflater.inflate(resourceID,null);

        Entry entry = entries.get(position);

        if(entry != null){
            TextView t1 = (TextView) convertView.findViewById(R.id.list_item1);
            TextView t2 = (TextView) convertView.findViewById(R.id.list_item2);
            if(entry.getName() != null){
                t1.setText(entry.getName());
            }
            if(entry.getCalories() != 0){
                t2.setText(String.valueOf(entry.getCalories()));
            }
        }
        return convertView;
    }
}
