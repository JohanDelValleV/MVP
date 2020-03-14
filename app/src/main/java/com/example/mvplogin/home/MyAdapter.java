package com.example.mvplogin.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mvplogin.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Career> careers;

    public MyAdapter(Context context, int layout, ArrayList<Career> careers) {
        this.context = context;
        this.layout = layout;
        this.careers = careers;
    }

    @Override
    public int getCount() {
        return this.careers.size();
    }

    @Override
    public Object getItem(int position) {
        return this.careers.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.list_item, null);
        Career currentCareer = careers.get(position);
        TextView textView = v.findViewById(R.id.textView);
        textView.setText(currentCareer.getName());
        return v;
    }
}
