package com.example.mireclifour.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.adapter.AdapterColor;
import com.example.mireclifour.adapter.AdapterSize;
import com.example.mireclifour.model.Attribute;
import com.example.mireclifour.model.Product;
import com.example.mireclifour.model.ProductOptions;
import com.example.mireclifour.model.User;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.CustomTypefaceSpan;
import com.example.mireclifour.utils.UserService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    String brand, ordername, order_code, orderprice, saleprice, order_listid, order_detail, formattedDate = "", select_color_name = "", select_size_name, base_img;
    Button sepete_ekle, cart_detail_btn, add_btn;
    ListView list_size, list_color;
    JSONArray order_images;
    List<ProductOptions> order_option_array = new ArrayList<>();
    SpannableStringBuilder SS;
    Product product = new Product();
    int sizeee = 0;
    ArrayList<Product> sizename = new ArrayList<>();
    ArrayList<Product> colorname = new ArrayList<>();
    TextView order_detail_brand, order_detail_name, order_detail_price, order_detail_desc;
    ImageView base_image;
    AdapterColor adapter_color;
    AdapterSize adapter_size;
    public static TextView order_detail_qty;
    EditText order_detail_color, order_detail_size;
    ProgressDialog mdialog;
    TextInputLayout input_color, input_size;
    String user_id, type, product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences editor = getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        type = editor.getString("type", null);
        user_id = editor.getString("user_id", null);

        super.onCreate(savedInstanceState);
        if (type.equals("Supplier")) {
            setContentView(R.layout.activity_order_detail2);
        } else if (type.equals("Customer")) {
            setContentView(R.layout.activity_order_detail1);
        }

        mdialog = new ProgressDialog(this);
        mdialog.setMessage(SplashActivity.variables_str);
        Intent itrrr = getIntent();
        order_listid = itrrr.getStringExtra("id");
        product_id = itrrr.getStringExtra("product_id");

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        formattedDate = df.format(c);

        getdata();
    }

    private void getdata() {
        mdialog.show();
        order_detail_qty = (TextView) findViewById(R.id.order_detail_qty);
        order_detail_brand = (TextView) findViewById(R.id.order_detail_brand);
        order_detail_brand.setTypeface(Constant.font_bold(getApplicationContext()));

        order_detail_desc = (TextView) findViewById(R.id.order_detail_desc);
        order_detail_desc.setTypeface(Constant.font_bold(getApplicationContext()));
        order_detail_desc.setText("Product Detail");

        input_color = (TextInputLayout) findViewById(R.id.input_color);
        input_color.setHint("Color");
        input_color.setTypeface(Constant.font_bold(this));

        input_size = (TextInputLayout) findViewById(R.id.input_size);
        input_size.setHint("Size");
        input_size.setTypeface(Constant.font_bold(this));

        order_detail_name = (TextView) findViewById(R.id.order_detail_name);
        order_detail_name.setTypeface(Constant.font_bold(getApplicationContext()));

        base_image = (ImageView) findViewById(R.id.base_image);

        order_detail_color = (EditText) findViewById(R.id.order_detail_color);
        order_detail_color.setTypeface(Constant.font_app(getApplicationContext()));
        order_detail_color.setInputType(InputType.TYPE_NULL);
        order_detail_color.setFocusable(false);
        order_detail_color.setVisibility(View.GONE);

        order_detail_size = (EditText) findViewById(R.id.order_detail_size);
        order_detail_size.setTypeface(Constant.font_app(getApplicationContext()));
        order_detail_size.setInputType(InputType.TYPE_NULL);
        order_detail_size.setFocusable(false);
        order_detail_size.setVisibility(View.GONE);

        order_detail_price = (TextView) findViewById(R.id.order_detail_price);
        order_detail_price.setTypeface(Constant.font_bold(getApplicationContext()));

        sepete_ekle = (Button) findViewById(R.id.sepete_ekle);
        sepete_ekle.setText("Add");
        sepete_ekle.setTypeface(Constant.font_bold(getApplicationContext()));
        sepete_ekle.setTextColor(Color.parseColor("#FFFFFEFE"));
        sepete_ekle.setBackgroundResource(R.color.black);
        sepete_ekle.setVisibility(View.VISIBLE);
        sepete_ekle.setOnClickListener(this);

        cart_detail_btn = (Button) findViewById(R.id.cart_detail_btn);
        add_btn = (Button) findViewById(R.id.add);

        order_detail_color.setOnClickListener(this);
        order_detail_size.setOnClickListener(this);

        SharedPreferences prefs_qty = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        String item_qty = prefs_qty.getString("line_count", null);
        if (item_qty != null && type.equals("Customer")) {
            Log.e("line_count", String.valueOf(item_qty));
            order_detail_qty.setVisibility(View.VISIBLE);
            order_detail_qty.setText(item_qty);
        } else if (type.equals("Customer")) {
            order_detail_qty.setVisibility(View.GONE);
        }

        order_detail_desc.setOnClickListener(this);
        base_image.setOnClickListener(this);
        if (type.equals("Supplier")) {
            add_btn.setOnClickListener(this);
        } else {
            cart_detail_btn.setOnClickListener(this);
        }
        getProductDetailData();
    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    private void getProductDetailData() {
        UserService service = getRetrofit().create(UserService.class);
        Call<List<Product>> products = service.getProducts(product_id);

        products.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mdialog.show();
                for (int i = 0; i < response.body().size(); i++) {
                    if (response.body().get(i).getId().equals(order_listid)) {

                        product = response.body().get(i);

                        ordername = product.getProduct_name();
                        order_detail = product.getDescription();
                        order_detail_name.setText(ordername);

                        brand = product.getBrand();
                        order_detail_brand.setText(brand);

                        order_code = product.getId();
                        Picasso.with(getApplicationContext()).load(product.getBase_image()).placeholder(R.drawable.bglogo).into(base_image);
                        base_img = product.getBase_image();

                        orderprice = product.getNormal_price() + SplashActivity.price_symbol_val;
                        saleprice = product.getSale_price() + SplashActivity.price_symbol_val;

                        if (orderprice.equals(saleprice)) {
                            SpannableStringBuilder SS = new SpannableStringBuilder(orderprice);
                            SS.setSpan(new CustomTypefaceSpan("", Constant.font_light(getApplicationContext())), 0, orderprice.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                            //SS.setSpan(new RelativeSizeSpan(1f), 0, 6, 0);
                            order_detail_price.setText(SS);

                        } else {
                            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                            SS = new SpannableStringBuilder(orderprice + "  " + saleprice);
                            SS.setSpan(new CustomTypefaceSpan("", Constant.font_light(getApplicationContext())), 0, saleprice.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                            SS.setSpan(new CustomTypefaceSpan("", Constant.font_medium(getApplicationContext())), orderprice.length() + 1, saleprice.length() + orderprice.length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                            SS.setSpan(new RelativeSizeSpan(0.8f), 0, 8, 0);
                            //SS.setSpan(new RelativeSizeSpan(1f), 9, 13, 0);
                            SS.setSpan(new ForegroundColorSpan(Color.RED), 0, orderprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            SS.setSpan(strikethroughSpan, 0, saleprice.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                            order_detail_price.setText(SS);
                            order_detail_price.setTypeface(Typeface.DEFAULT_BOLD);
                        }
                        mdialog.dismiss();

                        order_images = new JSONArray();
                        order_images.put(base_img);
                        System.out.println(product.getProduct_options());
                        order_option_array = product.getProduct_options();
                        for (int j = 0; j < order_option_array.size(); j++) {
                            Attribute attibute_obj = order_option_array.get(j).getAttributes();

                            if (attibute_obj.getRenk() != null && attibute_obj.getBeden() != null) {
                                Product color_data = new Product();
                                color_data.setColor(attibute_obj.getRenk());
                                boolean have_color = false;
                                for (int k = 0; k < colorname.size(); k++) {
                                    if (attibute_obj.getRenk().equals(colorname.get(k).getColor())) {
                                        have_color = true;
                                        break;
                                    }
                                }
                                if (!have_color) {
                                    colorname.add(color_data);
                                }

                                order_detail_size.setVisibility(View.VISIBLE);
                                order_detail_color.setVisibility(View.VISIBLE);
                                order_detail_color.setText(colorname.get(0).getColor());
                                select_color_name = colorname.get(0).getColor();

                                if (attibute_obj.getRenk().equals(select_color_name)) {
                                    Product size_data = new Product();
                                    size_data.setSize(attibute_obj.getBeden());
                                    sizename.add(size_data);
                                    order_detail_size.setText(sizename.get(0).getSize());
                                    select_size_name = sizename.get(0).getSize();
                                }
                            } else if (attibute_obj.getRenk() != null) {
                                Product color_data = new Product();
                                color_data.setColor(attibute_obj.getRenk());
                                boolean have_color = false;
                                for (int k = 0; k < colorname.size(); k++) {
                                    if (attibute_obj.getRenk().equals(colorname.get(k).getColor())) {
                                        have_color = true;
                                        break;
                                    }
                                }
                                if (!have_color) {
                                    colorname.add(color_data);
                                    Log.e("renk", attibute_obj.getRenk());
                                }
                                order_detail_color.setVisibility(View.VISIBLE);
                                order_detail_color.setText(colorname.get(0).getColor());
                                select_color_name = colorname.get(0).getColor();

                            } else if (attibute_obj.getBeden() != null) {

                                order_detail_size.setVisibility(View.VISIBLE);
                                Product size_data = new Product();
                                size_data.setSize(attibute_obj.getBeden());
                                sizename.add(size_data);
                                order_detail_size.setText(sizename.get(0).getSize());
                                select_size_name = sizename.get(0).getSize();

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    private void showwindow() {
        @SuppressLint("ResourceType")
        Dialog customdialog = new Dialog(new ContextThemeWrapper(this, R.style.DialogAnimation));
        Window window = customdialog.getWindow();

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setContentView(R.layout.prodetail_dialog);
        customdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        window.setGravity(Gravity.BOTTOM);

        TextView price_detail_txt = (TextView) customdialog.findViewById(R.id.price_detail_txt);
        price_detail_txt.setText("Price :");
        TextView code_detail_txt = (TextView) customdialog.findViewById(R.id.code_detail_txt);
        code_detail_txt.setText("Product Code :");
        TextView brand_detail_txt = (TextView) customdialog.findViewById(R.id.brand_detail_txt);
        brand_detail_txt.setText("Brand :");
        TextView product_detail_dialog_code = (TextView) customdialog.findViewById(R.id.product_detail_dialog_code);
        product_detail_dialog_code.setText(order_code);
        TextView product_detail_dialog_price = (TextView) customdialog.findViewById(R.id.product_detail_dialog_price);

        if (orderprice.equals(saleprice)) {
            SpannableStringBuilder SS = new SpannableStringBuilder(orderprice);
            SS.setSpan(new CustomTypefaceSpan("", Constant.font_light(getApplicationContext())), 0, orderprice.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            //SS.setSpan(new RelativeSizeSpan(1f), 0, 6, 0);
            product_detail_dialog_price.setText(SS);

        } else {
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            SS = new SpannableStringBuilder(orderprice + "  " + saleprice);
            SS.setSpan(new CustomTypefaceSpan("", Constant.font_light(getApplicationContext())), 0, saleprice.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            SS.setSpan(new CustomTypefaceSpan("", Constant.font_medium(getApplicationContext())), orderprice.length() + 1, saleprice.length() + orderprice.length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            SS.setSpan(new RelativeSizeSpan(0.9f), 0, 7, 0);
            //SS.setSpan(new RelativeSizeSpan(1f), 9, 13, 0);
            SS.setSpan(new ForegroundColorSpan(Color.RED), 0, orderprice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            SS.setSpan(strikethroughSpan, 0, saleprice.length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            product_detail_dialog_price.setText(SS);

        }

        TextView product_detail_dialog_brand = (TextView) customdialog.findViewById(R.id.product_detail_dialog_brand);
        product_detail_dialog_brand.setText(brand);
        TextView product_detail_dialog_name = (TextView) customdialog.findViewById(R.id.product_detail_dialog_name);
        product_detail_dialog_name.setText(ordername);
        TextView product_detail_dialog_desc = (TextView) customdialog.findViewById(R.id.product_detail_dialog_desc);
        product_detail_dialog_desc.setText(order_detail);
        customdialog.show();
    }


    private void colordata(final Dialog dialog1) {
        list_color = (ListView) dialog1.findViewById(R.id.list);
        adapter_color = new AdapterColor(getApplicationContext(), colorname);
        list_color.setAdapter(adapter_color);
        dialog1.show();

        list_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order_detail_color.setText(colorname.get(position).getColor());
                select_color_name = colorname.get(position).getColor();

                if (sizename.size() > 0) {
                    productsize();
                } else {
                    getfinalarray();
                }
                dialog1.dismiss();
            }
        });
    }

    private void sizedata(final Dialog dialog) {
        list_size = (ListView) dialog.findViewById(R.id.list);
        adapter_size = new AdapterSize(getApplicationContext(), sizename, sizeee);
        list_size.setAdapter(adapter_size);
        dialog.show();

        list_size.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order_detail_size.setText(sizename.get(position).getSize());
                select_size_name = sizename.get(position).getSize();
                dialog.dismiss();
                getfinalarray();
            }
        });
    }

    private void productsize() {
        for (int p = 0; p < sizename.size(); p++) {
            sizename.get(p).getSize();
            if (sizename.get(p).getSize().equals(select_color_name)) {
                sizeee = p;
                adapter_size = new AdapterSize(getApplicationContext(), sizename, sizeee);
                list_size.setAdapter(adapter_size);
            }
        }
    }

    @SuppressLint("ColorResource")
    private void getfinalarray() {
        if (order_option_array != null) {
            for (int j = 0; j < order_option_array.size(); j++) {
                Attribute attibute_obj = order_option_array.get(j).getAttributes();
                ProductOptions index_arry = new ProductOptions();
                if (attibute_obj.getRenk() != null && attibute_obj.getBeden() != null) {
                    if (attibute_obj.getRenk().equals(select_color_name) && attibute_obj.getBeden().equals(select_size_name)) {
                        index_arry = (ProductOptions) order_option_array.get(j);
                    }
                } else if (attibute_obj.getRenk() != null) {
                    if (attibute_obj.getRenk().equals(select_color_name)) {
                    }
                } else if (attibute_obj.getBeden() != null) {
                    if (attibute_obj.getBeden().equals(select_size_name)) {
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.order_detail_size: {
                final Dialog dialog = new Dialog(ProductDetailActivity.this);
                dialog.setContentView(R.layout.citylistview);
                sizedata(dialog);
                break;
            }

            case R.id.order_detail_color: {
                final Dialog dialog1 = new Dialog(ProductDetailActivity.this);
                dialog1.setContentView(R.layout.citylistview);
                colordata(dialog1);
                break;
            }

            case R.id.order_detail_desc: {
                showwindow();
                break;
            }

            case R.id.base_image: {
                Intent base_img_it = new Intent(this, EnlargeImageActivity.class);
                base_img_it.putExtra("base_image", base_img);
                if (order_images.length() > 0) {
                    base_img_it.putExtra("array_img", order_images.toString());
                }
                startActivity(base_img_it);
                break;
            }

            case R.id.sepete_ekle: {
                if (type.equals("Customer")) {
                    Constant.showToastMessage(getApplicationContext(), "ADD TO CART", 2);

                    SharedPreferences.Editor editor = getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE).edit();
                    editor.putString("product_id", product_id);
                    editor.apply();

                    UserService service = getRetrofit().create(UserService.class);
                    Call<User> user = service.getUser(user_id);
                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.e("success", response.body().toString());
                            List<Product> cart = response.body().getProducts();
                            cart.add(product);
                            response.body().setProducts(cart);

                            Call<Void> users = service.putUser(response.body());
                            users.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("failure", t.getLocalizedMessage());
                        }
                    });
                } else {
                    startActivity(new Intent(ProductDetailActivity.this, AddProduct.class));
                }
                break;
            }

            case R.id.cart_detail_btn: {
                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
                break;
            }
            case R.id.add: {
                startActivity(new Intent(ProductDetailActivity.this, AddProduct.class));
                break;
            }
        }
    }
}