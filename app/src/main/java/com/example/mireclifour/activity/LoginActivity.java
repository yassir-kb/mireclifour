package com.example.mireclifour.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.model.User;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    User user = new User();
    Button ll_login_btn, ll_signup;
    TextView logintext;
    EditText login_mail, login_password;
    WebView webView;
    ImageView login_back_img;
    TextInputLayout input_layout_mail, input_layout_password;


    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        getweight();
    }

    private void getweight() {
        webView = (WebView) findViewById(R.id.webview1);
        ll_login_btn = (Button) findViewById(R.id.ll_login_btn);
        login_back_img = (ImageView) findViewById(R.id.login_back_img);

        input_layout_mail = (TextInputLayout) findViewById(R.id.input_layout_mail);
        input_layout_mail.setHint("E-mail");
        input_layout_mail.setTypeface(Constant.font_medium(this));

        input_layout_password = (TextInputLayout) findViewById(R.id.input_layout_password);
        input_layout_password.setHint("Password");
        input_layout_password.setTypeface(Constant.font_medium(this));

        logintext = (TextView) findViewById(R.id.logintext);
        logintext.setText("By signing in, you are agreeing to our Terms of Use and our Privacy Policy.");

        login_mail = (EditText) findViewById(R.id.login_mail);
        login_mail.setTypeface(Constant.font_app(this));

        login_password = (EditText) findViewById(R.id.login_password);
        login_password.setTypeface(Constant.font_app(this));

        ll_signup = (Button) findViewById(R.id.ll_signup);
        ll_login_btn.setText("Login");

        logininfo();

        ll_signup.setText("Sign up");
        ll_login_btn.setOnClickListener(this);
        ll_signup.setOnClickListener(this);
    }

    private void logininfo() {
        String main_str = "By signing in, you are agreeing to our Terms of Use and our Privacy Policy.";
        String privarcy_str = "Privacy Policy";
        final Spannable spannable = new SpannableString(main_str);

        int index_privarcy = main_str.indexOf(privarcy_str);
        int index_privarcy_last = privarcy_str.length();
        spannable.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), index_privarcy, index_privarcy + index_privarcy_last, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        logintext.setTypeface(Constant.font_bold(getApplicationContext()));
        logintext.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_signup: {
                Intent update_it = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(update_it);
                break;
            }

            case R.id.ll_login_btn: {
                if (isEmpty()) {
                    ApiCallLogin();
                    break;
                }
            }
        }
    }

    private boolean isEmpty() {
        if (login_mail.getText().toString().trim().isEmpty() || login_password.getText().toString().isEmpty()) {
            Constant.showToastMessage(LoginActivity.this,
                    "Required all field.", 1);
            return false;
        }

        if (login_password.getText().toString().length() < 6) {
            Constant.showToastMessage(getApplicationContext(),
                    "Minimum 6 character required for password.", 1);
            return false;
        }
        if (!login_mail.getText().toString().trim().matches(Constant.email_validate)) {
            Constant.showToastMessage(LoginActivity.this,
                    "Invalid mail.", 1);
            return false;
        }

        return true;
    }

    private void ApiCallLogin() {

        SharedPreferences.Editor editor = getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE).edit();
        editor.apply();
        customerdetail();

    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void customerdetail() {
        UserService service = getRetrofit().create(UserService.class);
        Call<List<User>> users = service.getAllUsers();

        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    Log.e("success", response.body().toString());
                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getMail().equals(login_mail.getText().toString())
                                && response.body().get(i).getPassword().equals(login_password.getText().toString())) {
                            user = response.body().get(i);

                            SharedPreferences.Editor editor = getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE).edit();
                            editor.putString("user_id", user.getId());
                            editor.putString("firstname", user.getFirstname());
                            editor.putString("lastname", user.getLastname());
                            editor.putString("mail", user.getMail());
                            editor.putString("mobile", user.getNumber());
                            editor.putString("dob", user.getDob());
                            editor.putString("gender", user.getGender());
                            editor.putString("type", user.getType());
                            editor.putString("logo", user.getType());
                            editor.putString("password", user.getPassword());
                            editor.apply();

                            startActivity(new Intent(LoginActivity.this, MireclifourActivity.class));

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }
}