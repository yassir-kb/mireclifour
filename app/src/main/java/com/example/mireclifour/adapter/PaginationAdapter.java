package com.example.mireclifour.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.activity.ProductDetailActivity;
import com.example.mireclifour.model.Product;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.CustomTypefaceSpan;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<Product> productlist;
    private Context context;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String user_id = "";

    public PaginationAdapter(Context context, String product_id) {
        this.context = context;
        this.user_id = product_id;
        productlist = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.product, parent, false);
                viewHolder = new HeroVH(viewItem);
                break;

            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Product result = productlist.get(position);

        holder.setIsRecyclable(false);
        switch (getItemViewType(position)) {

            case ITEM:
                final HeroVH movieVH = (HeroVH) holder;
                movieVH.order_recycler_name.setText(result.getProduct_name());
                movieVH.order_recycler_name.setTypeface(Constant.font_app(context));
                String img = result.getBase_image();
                img = img.replaceAll(" ", "%20");

                if (img.equals("")) {
                    movieVH.order_recycler_image.setImageResource(R.drawable.logo_bg);
                } else {

                    Picasso.with(context).load(img).error(R.drawable.logo_bg).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE)
                            .placeholder(R.drawable.logo_bg).into(movieVH.order_recycler_image);
                }

                String firstWord = result.getNormal_price();
                String secondWord = result.getSale_price();

                if (firstWord.equals(secondWord)) {
                    SpannableStringBuilder SS = new SpannableStringBuilder(firstWord);
                    SS.setSpan(new CustomTypefaceSpan("", Constant.font_bold(context)), 0, firstWord.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    //SS.setSpan(new RelativeSizeSpan(1f), 0, 6, 0);
                    movieVH.order_recycler_price.setText(SS);
                    movieVH.order_recycler_price.setTypeface(Constant.font_bold(context));
                    movieVH.indirm_txt.setVisibility(View.GONE);
                } else {
                    SpannableStringBuilder SS = new SpannableStringBuilder(firstWord + " " + secondWord);
                    SS.setSpan(new CustomTypefaceSpan("", Constant.font_app(context)), 0, firstWord.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    SS.setSpan(new CustomTypefaceSpan("", Constant.font_bold(context)), firstWord.length() + 1, firstWord.length() + secondWord.length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    SS.setSpan(new RelativeSizeSpan(0.9f), 0, 7, 0);
                    //SS.setSpan(new RelativeSizeSpan(1f), 9, 13, 0);
                    StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                    SS.setSpan(new ForegroundColorSpan(Color.RED), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SS.setSpan(strikethroughSpan, 0, firstWord.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    movieVH.order_recycler_price.setText(SS);
                    movieVH.indirm_txt.setText("Discount");
                    movieVH.indirm_txt.setTypeface(Constant.font_app(context));
                    movieVH.indirm_txt.setVisibility(View.VISIBLE);

                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent product_it = new Intent(context, ProductDetailActivity.class);
                        product_it.putExtra("id", productlist.get(position).getId());
                        product_it.putExtra("product_id", user_id);
                        context.startActivity(product_it);
                    }
                });

                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mProgressBar.setVisibility(View.GONE);
                } else {
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return productlist == null ? 0 : productlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM;
        } else {
            return (position == productlist.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }
    }

    public void add(Product r) {
        productlist.add(r);
        notifyItemInserted(productlist.size() - 1);
    }

    public void remove(Product r) {
        int position = productlist.indexOf(r);
        if (position > -1) {
            productlist.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected static class HeroVH extends RecyclerView.ViewHolder {
        public ImageView order_recycler_image;
        public static TextView order_recycler_price, order_recycler_name, indirm_txt;

        public HeroVH(View itemView) {
            super(itemView);

            order_recycler_price = (TextView) itemView.findViewById(R.id.order_recycler_price);
            order_recycler_name = (TextView) itemView.findViewById(R.id.order_recycler_name);
            order_recycler_image = (ImageView) itemView.findViewById(R.id.order_recycler_image);
            indirm_txt = (TextView) itemView.findViewById(R.id.indirm_txt);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            mProgressBar.setIndeterminate(true);

        }
    }
}