package com.redrock.liye.redrock_lv2.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.redrock.liye.redrock_lv2.R;

import java.util.List;

/**
 * Created by a on 2016/2/26.
 */
public class AreaAdapter extends ArrayAdapter<Area> {
    private int resourceId;
    public AreaAdapter(Context context, int textViewResourceId,List<Area> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Area area = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView areaName = (TextView) view.findViewById(R.id.area_name);
        areaName.setText(area.getName());
        return view;
    }
}
