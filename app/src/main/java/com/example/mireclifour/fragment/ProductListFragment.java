package com.example.mireclifour.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.activity.MireclifourActivity;
import com.example.mireclifour.activity.SplashActivity;
import com.example.mireclifour.adapter.PaginationAdapter;
import com.example.mireclifour.model.Product;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.PaginationScrollListener;
import com.example.mireclifour.utils.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductListFragment extends Fragment implements View.OnClickListener {

    String user_id;
    RecyclerView product_list_recycler_view;
    PaginationAdapter adapter_list;
    ProgressDialog pDialog;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    public static int currentPage = PAGE_START;
    TextView loading_txt;
    LinearLayoutManager linearLayoutManager;
    LinearLayout progressBar;
    ImageView grid_img, list_img;
    int numofcolum = 2;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;
    int total_page = 1;

    public ProductListFragment() {
    }


    @SuppressLint({"NewApi", "NotifyDataSetChanged"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_product_list, container, false);

        product_list_recycler_view = (RecyclerView) view.findViewById(R.id.product_list_recycler_view);
        grid_img = (ImageView) view.findViewById(R.id.grid_img);

        MireclifourActivity.app_bar_main.setVisibility(View.VISIBLE);

        list_img = (ImageView) view.findViewById(R.id.list_img);
        loading_txt = (TextView) view.findViewById(R.id.loading_txt);
        loading_txt.setText(SplashActivity.variables_str);
        progressBar = (LinearLayout) view.findViewById(R.id.progressBar);

        user_id = getArguments().getString("user_id");

        Bundle userrr = this.getArguments();
        String current_page = userrr.getString("current_page");

        if (current_page != null) {
            currentPage = 1;
        } else {
            currentPage = PAGE_START;
        }

        grid_img.setOnClickListener(this);
        list_img.setOnClickListener(this);
        setSelected(grid_img);

        linearLayoutManager = new GridLayoutManager(getContext(), numofcolum);
        int a = getResources().getConfiguration().orientation;
        adapter_list = new PaginationAdapter(getContext(), user_id);
        product_list_recycler_view.setAdapter(adapter_list);

        if (getActivity().getResources().getBoolean(R.bool.isTablet)) {
            if (a == Configuration.ORIENTATION_LANDSCAPE) {
                gaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, 1);
            } else {
                gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
            }

        } else {
            if (a == Configuration.ORIENTATION_LANDSCAPE) {
                gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
            } else {
                gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            }

        }
        product_list_recycler_view.setLayoutManager(gaggeredGridLayoutManager);
        product_list_recycler_view.setLayoutManager(linearLayoutManager);
        product_list_recycler_view.setHasFixedSize(true);
        product_list_recycler_view.getAdapter().notifyDataSetChanged();
        product_list_recycler_view.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void loadMoreItems() {
                if (total_page >= currentPage) {

                }

            }

            @Override
            public boolean isLastPage() {

                return isLastPage;
            }

            @Override
            public boolean isLoading() {

                return isLoading;
            }
        });
        getProductListData();
        return view;
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getActivity().getResources().getBoolean(R.bool.isTablet)) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gaggeredGridLayoutManager.setSpanCount(4);
            } else {
                gaggeredGridLayoutManager.setSpanCount(3);
            }

        } else {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gaggeredGridLayoutManager.setSpanCount(3);
            } else {
                gaggeredGridLayoutManager.setSpanCount(2);
            }
        }

        product_list_recycler_view.setLayoutManager(gaggeredGridLayoutManager);
        product_list_recycler_view.getAdapter().notifyDataSetChanged();
    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getProductListData() {
        UserService service = getRetrofit().create(UserService.class);
        Call<List<Product>> products = service.getProducts(user_id);

        products.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.e("success", response.body().toString());

                pDialog = new ProgressDialog(getContext());
                pDialog.setMessage(SplashActivity.variables_str);
                pDialog.show();

                if (currentPage < total_page) {
                    progressBar.setVisibility(View.VISIBLE);
                    pDialog.dismiss();

                }
                pDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                int page_count = 50;
                if (page_count > 0) {
                    total_page = 7;
                    int currentPage = 1;
                    if (total_page >= currentPage) {
                        for (int j = 0; j < response.body().size(); j++) {
                            Product slideproduct = response.body().get(j);

                            slideproduct.setNormal_price(slideproduct.getNormal_price() + SplashActivity.price_symbol_val);
                            slideproduct.setSale_price(slideproduct.getSale_price() + SplashActivity.price_symbol_val);

                            pDialog.dismiss();

                        }
                        if (currentPage > 2) {
                            pDialog.dismiss();
                            isLoading = false;
                        }

                        for (int k = 0; k < response.body().size(); k++) {
                            adapter_list.add(response.body().get(k));
                        }
                        if (currentPage != total_page) {
                            pDialog.dismiss();
                        } else {
                            isLastPage = true;
                        }


                    } else {
                        Constant.showToastMessage(getContext(), "No record Found", 1);
                        pDialog.dismiss();
                    }
                } else {
                    Constant.showToastMessage(getContext(), "No record Found", 1);
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.grid_img: {
                LinearLayoutManager mLayoutManager = new GridLayoutManager(getContext(), numofcolum);
                product_list_recycler_view.setLayoutManager(mLayoutManager);
                setUnselected(list_img);
                setSelected(grid_img);
                break;
            }

            case R.id.list_img: {
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                product_list_recycler_view.setLayoutManager(mLayoutManager);
                setUnselected(grid_img);
                setSelected(list_img);
                break;
            }
        }
    }

    public void setSelected(ImageView grid_img) {
        grid_img.setColorFilter(getContext().getResources().getColor(R.color.black));
    }

    public void setUnselected(ImageView grid_img) {
        grid_img.setColorFilter(getContext().getResources().getColor(R.color.white));
    }
}