package com.example.mireclifour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mireclifour.R;
import com.example.mireclifour.adapter.AdapterEnlargeImage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class EnlargeImageActivity extends AppCompatActivity {


    String base_image, array_img;
    List<Object> img_list = new ArrayList<Object>();
    ViewPager full_viewpager;
    AdapterEnlargeImage full_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlargeimage1);
        full_viewpager = (ViewPager) findViewById(R.id.full_viewpager);
        Intent itrrr = getIntent();
        base_image = itrrr.getStringExtra("base_image");
        array_img = itrrr.getStringExtra("array_img");

        try {
            JSONArray new_arry = new JSONArray(array_img);
            for (int i = 0; i < new_arry.length(); i++) {
                img_list.add(new_arry.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getimage();

    }

    private void getimage() {
        full_pager = new AdapterEnlargeImage(getApplicationContext(), img_list);
        full_viewpager.setAdapter(full_pager);
        full_viewpager.setCurrentItem(0);
    }
}