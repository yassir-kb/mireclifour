package com.example.mireclifour.fragment;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mireclifour.R;
import com.example.mireclifour.activity.MireclifourActivity;
import com.example.mireclifour.activity.SplashActivity;
import com.example.mireclifour.adapter.AdapterExtendableList;
import com.example.mireclifour.adapter.ShoppingAdapter;
import com.example.mireclifour.model.MainItems;
import com.example.mireclifour.model.User;
import com.example.mireclifour.utils.RecyclerTouchListener;
import com.example.mireclifour.utils.UserService;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductFragment extends Fragment {
    RecyclerView productlist_recyclerview;
    String user_id;
    List<MainItems> data = new ArrayList<MainItems>();
    ShoppingAdapter shoppingmainAdapter;
    String formattedDate;
    public static String new_id_list;
    ProgressDialog pDialog;

    public ProductFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list_fragment, container, false);
        productlist_recyclerview = (RecyclerView) view.findViewById(R.id.productlist_recyclerview);

        MireclifourActivity.app_bar_main.setVisibility(View.VISIBLE);

        user_id = getArguments().getString("user_id");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        productlist_recyclerview.setLayoutManager(mLayoutManager);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        formattedDate = df.format(c);
        Log.e("format", formattedDate);
        getSupplierListData();
        return view;
    }


    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getSupplierListData() {
        UserService service = getRetrofit().create(UserService.class);
        Call<List<User>> suppliers = service.getAllUsers();
        suppliers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.e("success", response.body().toString());

                pDialog = new ProgressDialog(getContext());
                pDialog.setMessage(SplashActivity.variables_str);
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.show();

                data.clear();
                for (int j = 0; j < response.body().size(); j++) {
                    if (response.body().get(j).getType().equals("Supplier")) {
                        User usr = response.body().get(j);
                        MainItems supp = new MainItems(usr.getId(), usr.getFirstname() + " " + usr.getLastname(), usr.getLogo(), usr.getMail() + "/" + usr.getNumber());
                        data.add(supp);
                        pDialog.dismiss();
                    }
                }

                productlist_recyclerview.setHasFixedSize(true);
                shoppingmainAdapter = new ShoppingAdapter(getContext(), data);
                productlist_recyclerview.setAdapter(shoppingmainAdapter);
                productlist_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getContext(), productlist_recyclerview, new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        new_id_list = data.get(position).getId();

                        Fragment fragment = new ProductListFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        Bundle bundle = new Bundle();
                        AdapterExtendableList.new_obj = new JSONObject();
                        AdapterExtendableList.keyvalue_pair = new HashMap<String, List<String>>();

                        bundle.putString("user_id", new_id_list);

                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.layout_content, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }
}