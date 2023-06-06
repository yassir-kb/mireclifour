package com.example.mireclifour.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.model.Product;
import com.example.mireclifour.model.User;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.UserService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    String grand_total = "0";
    RecyclerView cart_recycler;
    Button cart_continue;
    String user_id, count, product_id;
    LinearLayout cart_layout;
    AdapterCart cart_list_adapter;
    TextView cart_empty_txt, cart_grandtotal, discount_cart;
    ProgressDialog progressDialog;
    Button shop_btn;
    String discount_amount = "0";
    List<Product> cart_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        user_id = prefs.getString("user_id", null);
        product_id = prefs.getString("product_id", null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart1);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(SplashActivity.variables_str);
        cart_layout = (LinearLayout) findViewById(R.id.cart_layout);
        cart_empty_txt = (TextView) findViewById(R.id.cart_empty_txt);
        shop_btn = (Button) findViewById(R.id.shop_btn);
        discount_cart = (TextView) findViewById(R.id.discount_cart);

        getData();
    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private void getData() {
        progressDialog.show();

        cart_recycler = (RecyclerView) findViewById(R.id.cart_recycler);
        cart_continue = (Button) findViewById(R.id.cart_continue);
        cart_continue.setText("Checkout");

        cart_grandtotal = (TextView) findViewById(R.id.cart_grandtotal);
        cart_grandtotal.setTypeface(Constant.font_medium(this));
        progressDialog.dismiss();
        cart_continue.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(SplashActivity.variables_str);
        progressDialog.show();

        getCartList();
    }

    public void getCartList() {
        UserService service = getRetrofit().create(UserService.class);
        cart_list.clear();

        Call<List<Product>> products = service.getProducts(user_id);
        products.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                for (int w = 0; w < response.body().size(); w++) {
                    cart_list.add(response.body().get(w));
                }
                for (int i = 0; i < cart_list.size(); i++) {
                    grand_total = String.valueOf(Double.parseDouble(grand_total) + Double.parseDouble(cart_list.get(i).getSale_price()));
                }

                String line_count = String.valueOf(cart_list.size());
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE).edit();
                editor.putString("line_count", line_count);
                editor.apply();


                if ((grand_total) != null) {

                    String f = String.format("%.2f", Double.parseDouble(grand_total));
                    cart_grandtotal.setText("Grand Total : " + " " + f + SplashActivity.price_symbol_val);
                    cart_grandtotal.setTypeface(Constant.font_bold(getApplicationContext()));
                    progressDialog.dismiss();
                }

                if (discount_amount != null) {

                    String f = String.format("%.2f", Double.parseDouble(discount_amount));
                    discount_cart.setText("Discount : " + " " + f + SplashActivity.price_symbol_val);
                    discount_cart.setTypeface(Constant.font_app(getApplicationContext()));
                    progressDialog.dismiss();

                }

                cart_recycler.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                cart_recycler.setLayoutManager(mLayoutManager);
                cart_list_adapter = new AdapterCart(getApplicationContext(), cart_list, count);
                cart_recycler.setAdapter(cart_list_adapter);
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
            case R.id.cart_continue:
                SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
                user_id = prefs.getString("user_id", null);

                if (user_id != null) {
                    Uri uri = Uri.parse("https://www.helloasso.com/associations/transaharienne/paiements/faire-un-don");
                    Intent itt = new Intent(CartActivity.this, ProductSucessActivity.class);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(itt);
                    startActivity(intent);

                } else {
                    startActivity(new Intent(CartActivity.this, LoginActivity.class));
                }
                break;
        }
    }

    public class AdapterCart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        List<Product> cart_list = new ArrayList<>();
        private static final int ITEM = 0;
        private static final int ITEMTOTAL = 1;
        String count1;

        public AdapterCart(Context mContext, List<Product> cart_list, String count) {
            this.context = mContext;
            this.cart_list = cart_list;
            this.count1 = count;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            switch (viewType) {
                case ITEM:
                    View viewItem = inflater.inflate(R.layout.cart_item, parent, false);
                    viewHolder = new orderclass(viewItem);
                    break;
                case ITEMTOTAL:
                    View viewLoading = inflater.inflate(R.layout.cart_empty, parent, false);
                    viewHolder = new ordertotal(viewLoading);
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final Product cart_list_item = cart_list.get(position);
            final orderclass movieVH = (orderclass) holder;

            movieVH.cart_ordername.setText(cart_list_item.getProduct_name());
            movieVH.cart_orderquantity.setText(cart_list_item.getQty());

            movieVH.cart_orderprice.setText(cart_list_item.getNormal_price() + SplashActivity.price_symbol_val);
            movieVH.total_price.setText(cart_list_item.getSale_price() + SplashActivity.price_symbol_val);

            String img = cart_list_item.getBase_image();
            img = img.replaceAll(" ", "%20");

            if (img.equals("")) {
                ((orderclass) holder).cart_orderimg.setImageResource(R.drawable.bglogo);
            } else {
                Picasso.with(context).load(img).error(R.drawable.logo_bg)
                        .placeholder(R.drawable.logo_bg).into(((orderclass) holder).cart_orderimg);
            }
            ((orderclass) holder).order_discount.setPaintFlags(((orderclass) holder).order_discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            movieVH.cart_ordername.setTypeface(Constant.font_bold(context));
            movieVH.cart_orderquantity.setTypeface(Constant.font_app(context));
            movieVH.cart_orderprice.setTypeface(Constant.font_bold(context));
            movieVH.total_price.setTypeface(Constant.font_bold(context));
            movieVH.cart_listprice.setText("Original Price : ");
            movieVH.cart_listprice.setTypeface(Constant.font_app(context));
            movieVH.cart_itemtotalprice.setText("Final Price : ");
            movieVH.cart_itemtotalprice.setTypeface(Constant.font_app(context));

            movieVH.item_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CartActivity.this);
                    alertDialogBuilder.setMessage("Are you sure ?");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    UserService service = getRetrofit().create(UserService.class);
                                    System.out.println(String.valueOf(position));
                                    Call<Void> dlt = service.deleteProduct(user_id, String.valueOf(position));
                                    dlt.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Log.e("success", user_id);
                                            Constant.showToastMessage(getApplicationContext(), "sucessfully delete from cart", 2);
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Log.e("failure", t.getLocalizedMessage());
                                        }
                                    });
                                }
                            });

                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });

            movieVH.cart_orderquantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        String orderquantity = movieVH.cart_orderquantity.getText().toString();
                        movieVH.cart_orderquantity.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
                        updateCart(Integer.parseInt(orderquantity));
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return cart_list.size();
        }
        protected class orderclass extends RecyclerView.ViewHolder {
            public ImageView cart_orderimg;
            public TextView order_discount, cart_itemtotalprice, total_price, cart_orderprice, cart_listprice, cart_ordername;
            public ImageView item_close, cart_plus, cart_minus;
            public EditText cart_orderquantity;

            public orderclass(final View itemView) {
                super(itemView);

                cart_orderimg = (ImageView) itemView.findViewById(R.id.cart_orderimg);
                order_discount = (TextView) itemView.findViewById(R.id.order_discount);
                cart_plus = (ImageView) itemView.findViewById(R.id.cart_plus);
                cart_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserService srv = getRetrofit().create(UserService.class);

                        Call<User> usr = srv.getUser(user_id);
                        usr.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {

                                int qty = Integer.parseInt(cart_list.get(getAdapterPosition()).getQty());
                                qty++;
                                User user = response.body();
                                cart_list.get(getAdapterPosition()).setQty(String.valueOf(qty));
                                for (int i = 0; i < cart_list.size(); i++) {
                                    System.out.println(cart_list.size());
                                }
                                user.setProducts(cart_list);

                                Call<Void> ord = srv.putUser(user);
                                updateCart(qty);

                                ord.enqueue(new Callback<Void>() {
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
                    }
                });

                cart_minus = (ImageView) itemView.findViewById(R.id.cart_minus);
                cart_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserService srv = getRetrofit().create(UserService.class);

                        Call<User> usr = srv.getUser(user_id);
                        usr.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {

                                int qty = Integer.parseInt(cart_list.get(getAdapterPosition()).getQty());
                                qty--;
                                User user = response.body();

                                cart_list.get(getAdapterPosition()).setQty(String.valueOf(qty));
                                user.setProducts(cart_list);

                                Call<Void> ord = srv.putUser(user);
                                updateCart(qty);

                                ord.enqueue(new Callback<Void>() {
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
                    }
                });

                cart_orderquantity = (EditText) itemView.findViewById(R.id.cart_orderquantity);
                cart_listprice = (TextView) itemView.findViewById(R.id.cart_listprice);
                cart_orderprice = (TextView) itemView.findViewById(R.id.cart_orderprice);
                cart_itemtotalprice = (TextView) itemView.findViewById(R.id.cart_itemtotalprice);
                total_price = (TextView) itemView.findViewById(R.id.total_price);
                cart_ordername = (TextView) itemView.findViewById(R.id.cart_ordername);
                item_close = (ImageView) itemView.findViewById(R.id.item_close);

            }
        }

        protected class ordertotal extends RecyclerView.ViewHolder {
            ImageView cart_emptyimg;

            public ordertotal(View itemView) {
                super(itemView);
                cart_emptyimg = (ImageView) itemView.findViewById(R.id.cart_emptyimg);
            }
        }
    }

    private void updateCart(int qty) {
        Constant.showToastMessage(getApplicationContext(), "Update", 2);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MireclifourActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }
}