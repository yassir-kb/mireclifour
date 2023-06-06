package com.example.mireclifour.fragment;


import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.adapter.AdapterUser;
import com.example.mireclifour.model.User;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SettingFragment extends Fragment {
    String firstname, lastname, mail;
    TextView user_name, user_mail;
    RecyclerView user_detail_recyclerview;
    AdapterUser adapterUser;
    String user_id;

    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        user_id = prefs.getString("user_id", null);
        String restoredText = prefs.getString("firstname", "");

        if (restoredText != null) {
            firstname = prefs.getString("firstname", "");//"No name defined" is the default value.
            lastname = prefs.getString("lastname", "");
            mail = prefs.getString("mail", "");

        }
        getdata(view);
        return view;
    }

    private void getdata(View view) {
        UserService srv = getRetrofit().create(UserService.class);
        Call<User> usr = srv.getUser(user_id);

        usr.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("success", response.body().toString());

                user_mail = (TextView) view.findViewById(R.id.user_mail);
                user_name = (TextView) view.findViewById(R.id.user_name);
                user_detail_recyclerview = (RecyclerView) view.findViewById(R.id.user_detail_recyclerview);
                user_name.setText(firstname + " " + lastname);

                user_name.setTypeface(Constant.font_app(getContext()));
                user_mail.setText(mail);

                user_mail.setTypeface(Constant.font_app(getContext()));
                adapterUser = new AdapterUser(getContext(), response.body());

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                user_detail_recyclerview.setLayoutManager(mLayoutManager);
                user_detail_recyclerview.setItemAnimator(new DefaultItemAnimator());
                user_detail_recyclerview.setAdapter(adapterUser);

                userlist();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    private void userlist() {
        adapterUser.notifyDataSetChanged();
    }
}
