package com.example.mireclifour.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.model.MainItems;
import com.example.mireclifour.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    private Context context;
    List<MainItems> category_data;

    public ShoppingAdapter(Context mContext, List<MainItems> category_data) {
        this.context = mContext;
        this.category_data = category_data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("ColorResource")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MainItems productcolor = category_data.get(position);
        holder.main_category_name.setText(productcolor.getName() + "/" + productcolor.getContact());
        String img = productcolor.getLogo();
        img = img.replaceAll(" ", "%20");

        if (img.equals("")) {
            holder.main_category_image.setImageResource(R.drawable.text_logo);
        } else {

            Picasso.with(context).load(img).error(R.drawable.logo_bg)
                    .placeholder(R.drawable.logo_bg).into(holder.main_category_image);
        }
        holder.main_category_name.setTypeface(Constant.font_app(context));
    }

    @Override
    public int getItemCount() {
        return category_data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView main_category_image;
        public TextView main_category_name;

        public ViewHolder(final View itemView) {
            super(itemView);
            main_category_name = (TextView) itemView.findViewById(R.id.main_category_name);
            main_category_image = (ImageView) itemView.findViewById(R.id.main_category_image);
        }
    }
}