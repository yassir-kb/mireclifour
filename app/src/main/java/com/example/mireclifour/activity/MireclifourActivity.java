package com.example.mireclifour.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.fragment.ProductFragment;
import com.example.mireclifour.fragment.ProductListFragment;
import com.example.mireclifour.fragment.SettingFragment;
import com.example.mireclifour.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MireclifourActivity extends AppCompatActivity implements View.OnClickListener {
    String formattedDate;
    DrawerLayout drawer;
    Drawable drawable;
    public static AppBarLayout app_bar_main;
    TextView logout_txt, user_detail_txt, main_login_txt;
    public static TextView cart_qty;
    FrameLayout layout_content;
    public static Button cart_btn, add_btn;
    String accType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        accType = prefs.getString("type", "");
        super.onCreate(savedInstanceState);

        if (accType.equals("Customer")) {
            setContentView(R.layout.activity_shopping1);
        } else {
            setContentView(R.layout.activity_shopping2);
        }
        multidiffusion();
        if (accType.equals("Customer")) {
            app_bar_main = (AppBarLayout) findViewById(R.id.app_bar_main1);
            supplierLst();
        } else {
            app_bar_main = (AppBarLayout) findViewById(R.id.app_bar_main2);
            productLst();
        }
    }

    private void productLst() {
        Fragment fragment = new ProductListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        bundle.putString("user_id", prefs.getString("user_id", ""));

        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.layout_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void supplierLst() {
        Fragment fragment = new ProductFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        bundle.putString("user_id", prefs.getString("1", ""));

        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.layout_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void multidiffusion() {
        layout_content = (FrameLayout) findViewById(R.id.layout_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        logout_txt = (TextView) findViewById(R.id.logout_txt);
        logout_txt.setTypeface(Constant.font_app(getApplicationContext()));
        logout_txt.setOnClickListener(this);

        if (accType.equals("Customer")) {
            cart_btn = (Button) findViewById(R.id.cart_btn);
            cart_btn.setOnClickListener(this);
            cart_qty = (TextView) findViewById(R.id.cart_qty);
        } else {
            add_btn = (Button) findViewById(R.id.add_btn);
            add_btn.setOnClickListener(this);
        }

        user_detail_txt = (TextView) findViewById(R.id.user_detail_txt);
        user_detail_txt.setOnClickListener(this);
        user_detail_txt.setText("User Detail");
        user_detail_txt.setTypeface(Constant.font_app(getApplicationContext()));

        main_login_txt = (TextView) findViewById(R.id.main_login_txt);
        main_login_txt.setOnClickListener(this);

        SharedPreferences prefs_qty = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        String item_qty = prefs_qty.getString("line_count", null);

        if (item_qty != null && accType.equals("Customer")) {
            Log.e("line_count", String.valueOf(item_qty));
            cart_qty.setVisibility(View.VISIBLE);
            cart_qty.setText(item_qty);
        } else if (accType.equals("Customer")) {
            cart_qty.setVisibility(View.GONE);
            cart_qty.setText("0");
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.drawernew_icon, getApplicationContext().getTheme());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_draweropen, R.string.navigation_drawerclose);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (drawer.isDrawerVisible(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);

                    } else {
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
            }
        });
        drawer.setDrawerListener(toggle);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        formattedDate = df.format(c);
        Log.e("format", formattedDate);

        SharedPreferences prefs1 = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        String user_id = prefs1.getString("user_id", null);

        if (user_id != null) {
            logout_txt.setVisibility(View.VISIBLE);
            logout_txt.setText("Logout");
            logout_txt.setTypeface(Constant.font_app(getApplicationContext()));
            user_detail_txt.setVisibility(View.VISIBLE);
        } else {
            main_login_txt.setVisibility(View.VISIBLE);
            main_login_txt.setText("Login");
            main_login_txt.setTypeface(Constant.font_app(getApplicationContext()));
            logout_txt.setVisibility(View.GONE);
            user_detail_txt.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cart_btn: {
                startActivity(new Intent(MireclifourActivity.this, CartActivity.class));
                break;
            }
            case R.id.add_btn: {
                startActivity(new Intent(MireclifourActivity.this, AddProduct.class));
                break;
            }

            case R.id.logout_txt: {

                SharedPreferences preferences = getSharedPreferences(Constant.MAIN_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(MireclifourActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

            case R.id.user_detail_txt: {

                Fragment fragment = new SettingFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_content, fragment);
                fragmentTransaction.addToBackStack(null);
                drawer.closeDrawers();
                fragmentTransaction.commit();
                break;
            }

            case R.id.main_login_txt: {
                startActivity(new Intent(MireclifourActivity.this, LoginActivity.class));
                break;
            }
        }
    }
}