package com.example.mireclifour.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.model.Attribute;
import com.example.mireclifour.model.Product;
import com.example.mireclifour.model.ProductOptions;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    String user_id = "";
    Button btn_add;
    ImageView add_back_img;
    TextInputLayout input_layout_id, input_layout_product_name, input_layout_size, input_layout_color, input_layout_brand, input_layout_description, input_layout_base_image, input_layout_normal_price, input_layout_sale_price, input_layout_qty;
    TextView text;
    EditText add_id, add_product_name, add_size, add_color, add_brand, add_description, add_base_image, add_normal_price, add_sale_price, add_qty;
    CheckBox chk_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        user_id = prefs.getString("user_id", null);

        getcast();
    }

    public void getcast() {
        input_layout_id = (TextInputLayout) findViewById(R.id.input_layout_id);
        input_layout_id.setHint("ID");
        input_layout_id.setTypeface(Constant.font_medium(this));

        input_layout_product_name = (TextInputLayout) findViewById(R.id.input_layout_product_name);
        input_layout_product_name.setTypeface(Constant.font_medium(this));
        input_layout_product_name.setHint("Product Name");

        input_layout_size = (TextInputLayout) findViewById(R.id.input_layout_size);
        input_layout_size.setHint("Size");
        input_layout_size.setTypeface(Constant.font_medium(this));

        input_layout_color = (TextInputLayout) findViewById(R.id.input_layout_color);
        input_layout_color.setHint("Color");
        input_layout_color.setTypeface(Constant.font_medium(this));

        input_layout_brand = (TextInputLayout) findViewById(R.id.input_layout_brand);
        input_layout_brand.setHint("Brand");
        input_layout_brand.setTypeface(Constant.font_medium(this));

        input_layout_description = (TextInputLayout) findViewById(R.id.input_layout_description);
        input_layout_description.setHint("Description");
        input_layout_description.setTypeface(Constant.font_medium(this));

        input_layout_base_image = (TextInputLayout) findViewById(R.id.input_layout_base_image);
        input_layout_base_image.setHint("Base Image");
        input_layout_base_image.setTypeface(Constant.font_medium(this));

        input_layout_normal_price = (TextInputLayout) findViewById(R.id.input_layout_normal_price);
        input_layout_normal_price.setHint("Normal Price");
        input_layout_normal_price.setTypeface(Constant.font_medium(this));

        input_layout_sale_price = (TextInputLayout) findViewById(R.id.input_layout_sale_price);
        input_layout_sale_price.setHint("Sale Price");
        input_layout_sale_price.setTypeface(Constant.font_medium(this));

        input_layout_qty = (TextInputLayout) findViewById(R.id.input_layout_qty);
        input_layout_qty.setHint("Quantity");
        input_layout_qty.setTypeface(Constant.font_medium(this));

        add_back_img = (ImageView) findViewById(R.id.add_back_img);

        add_id = (EditText) findViewById(R.id.add_id);
        add_id.setTypeface(Constant.font_app(this));

        add_product_name = (EditText) findViewById(R.id.add_product_name);
        add_product_name.setTypeface(Constant.font_app(this));

        add_size = (EditText) findViewById(R.id.add_size);
        add_size.setTypeface(Constant.font_app(this));

        add_color = (EditText) findViewById(R.id.add_size);
        add_color.setTypeface(Constant.font_app(this));

        add_brand = (EditText) findViewById(R.id.add_brand);
        add_brand.setTypeface(Constant.font_app(this));

        add_description = (EditText) findViewById(R.id.add_description);
        add_description.setTypeface(Constant.font_app(this));

        add_base_image = (EditText) findViewById(R.id.add_base_image);
        add_base_image.setTypeface(Constant.font_app(this));

        add_normal_price = (EditText) findViewById(R.id.add_normal_price);
        add_normal_price.setTypeface(Constant.font_app(this));

        add_sale_price = (EditText) findViewById(R.id.add_sale_price);
        add_sale_price.setTypeface(Constant.font_app(this));

        add_qty = (EditText) findViewById(R.id.add_qty);
        add_qty.setTypeface(Constant.font_app(this));


        text = (TextView) findViewById(R.id.text);
        chk_add = (CheckBox) findViewById(R.id.chk_add);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setText("Add");
        btn_add.setOnClickListener(this);

        logininfo();
    }

    private void logininfo() {
        String main_str = "By signing in, you are agreeing to our Terms of Use and our Privacy Policy.";
        final Spannable spannable = new SpannableString(main_str);
        text.setText(spannable);
        text.setTypeface(Constant.font_bold(getApplicationContext()));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_add: {
                Attribute attribute1 = new Attribute("1", "S", "Black");
                Attribute attribute2 = new Attribute("2", "L", "White");
                ProductOptions productOptions1 = new ProductOptions("1", "Small size", attribute1);
                ProductOptions productOptions2 = new ProductOptions("2", "Large size", attribute2);
                List<ProductOptions> lst = new ArrayList<>();
                lst.add(productOptions1);
                lst.add(productOptions2);

                Product product = new Product(
                        add_id.getText().toString(),
                        add_product_name.getText().toString(),
                        add_size.getText().toString(),
                        add_color.getText().toString(),
                        add_brand.getText().toString(),
                        add_description.getText().toString(),
                        add_base_image.getText().toString(),
                        add_normal_price.getText().toString(),
                        add_sale_price.getText().toString(),
                        add_qty.getText().toString(),
                        lst
                );
                postApi(product);
                Intent sucess_it = new Intent(AddProduct.this, MireclifourActivity.class);
                startActivity(sucess_it);
                break;
            }
        }
    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    private void postApi(Product product) {
        UserService service = getRetrofit().create(UserService.class);
        Call<Void> call = service.postProduct(user_id, product);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.e("success", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }
}