package com.example.mireclifour.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.mireclifour.R;
import com.example.mireclifour.utils.TouchImageView;

import java.util.List;


public class AdapterEnlargeImage extends PagerAdapter {
    private Context context;
    List<Object> detail_img;

    public AdapterEnlargeImage(Context context, List<Object> detail_img) {
        this.context = context;
        this.detail_img = detail_img;
    }

    @Override
    public int getCount() {
        return detail_img.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((ImageView) arg1);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imageView = new TouchImageView(container.getContext());
        imageView.setMaxZoom(2f);
        imageView.setPadding(0, 0, 0, 0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        String img = String.valueOf(detail_img.get(position));
        img = img.replaceAll(" ", "%20");
        Glide.with(context).load(img).placeholder(R.drawable.bglogo)
                .error(R.drawable.bglogo).into(imageView);

        ((ViewPager) container).addView(imageView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}