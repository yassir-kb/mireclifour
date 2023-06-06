package com.example.mireclifour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.model.Product;

import java.util.ArrayList;
import java.util.List;


public class AdapterSize extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Product> size_name;
    int size;

    public AdapterSize(Context context, ArrayList<Product> size_name, int sizeee) {
        this.context = context;
        this.size_name = size_name;
        this.size = sizeee;
    }

    @Override
    public int getCount() {
        return size_name.size();
    }

    @Override
    public Object getItem(int location) {
        return size_name.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.simple_list_item, null);


        TextView title = (TextView) convertView.findViewById(R.id.list_size_title);
        Product m = size_name.get(position);
        title.setText(m.getSize());

        return convertView;
    }
}