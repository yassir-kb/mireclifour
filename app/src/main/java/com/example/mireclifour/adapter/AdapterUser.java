package com.example.mireclifour.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.activity.CartActivity;
import com.example.mireclifour.activity.ChangePasswordActivity;
import com.example.mireclifour.model.User;
import com.example.mireclifour.utils.Constant;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyViewHolder> {
    private User user;
    Context context;
    ArrayList<String> userlist = new ArrayList<>();
    String user_id;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            title.setTypeface(Constant.font_app(context));
        }
    }


    public AdapterUser(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SharedPreferences editor = context.getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        user_id = editor.getString("user_id", null);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        userlist.add("Change Password");
        userlist.add("Check Cart/Add Product");

        String userDetail = userlist.get(position);
        holder.title.setText(userDetail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    Intent change_pass_it = new Intent(context, ChangePasswordActivity.class);
                    change_pass_it.putExtra("user_change_password", "user_change_password");
                    context.startActivity(change_pass_it);
                }
                if (position == 1) {
                    Intent change_pass_it = new Intent(context, CartActivity.class);
                    context.startActivity(change_pass_it);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}