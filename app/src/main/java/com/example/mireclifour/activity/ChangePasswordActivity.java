package com.example.mireclifour.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.model.User;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    Button change_password_btn;
    String user_id = "";
    TextView new_password_txt;
    EditText change_password, change_repassword, edit_email;
    ProgressDialog mdialog;
    TextInputLayout input_layout_change_repassword, input_layout_change_password, input_email;
    LinearLayout email_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password1);
        email_layout = (LinearLayout) findViewById(R.id.email_layout);

        SharedPreferences editor = getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        user_id = editor.getString("user_id", null);

        mdialog = new ProgressDialog(this);
        mdialog.setMessage(SplashActivity.variables_str);
        change_password = (EditText) findViewById(R.id.change_password);
        change_password.setTypeface(Constant.font_app(getApplicationContext()));

        input_layout_change_password = (TextInputLayout) findViewById(R.id.input_layout_changepassword);
        input_layout_change_password.setHint("Password");
        input_layout_change_password.setTypeface(Constant.font_medium(getApplicationContext()));

        input_layout_change_repassword = (TextInputLayout) findViewById(R.id.input_layout_change_repassword);
        input_layout_change_repassword.setHint("Re Password");
        input_layout_change_repassword.setTypeface(Constant.font_medium(getApplicationContext()));

        input_email = (TextInputLayout) findViewById(R.id.input_email);
        input_email.setHint("E Mail");
        input_email.setTypeface(Constant.font_medium(getApplicationContext()));

        new_password_txt = (TextView) findViewById(R.id.new_password_txt);
        new_password_txt.setText("New Password");
        new_password_txt.setTypeface(Constant.font_medium(getApplicationContext()));

        change_repassword = (EditText) findViewById(R.id.change_repassword);
        change_repassword.setTypeface(Constant.font_app(getApplicationContext()));

        new_password_txt.setTypeface(Constant.font_medium(getApplicationContext()));
        change_password_btn = (Button) findViewById(R.id.change_password_btn);
        change_password_btn.setText("Submit");
        change_password_btn.setOnClickListener(this);

        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_email.setInputType(InputType.TYPE_NULL);
        edit_email.setFocusable(false);

    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://transaharienne.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password_btn:
                if (isEmpty()) {
                    UserService srv = getRetrofit().create(UserService.class);

                    Call<User> usr = srv.getUser(user_id);
                    usr.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            response.body().setPassword(change_password.getText().toString());

                            Call<Void> ord = srv.putUser(response.body());
                            ord.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Log.e("success", response.body().toString());
                                    Constant.showToastMessage(getApplicationContext(), "Password changed successfully", 2);
                                    startActivity(new Intent(ChangePasswordActivity.this, MireclifourActivity.class));
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("failure", t.getLocalizedMessage());
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("failure", t.getLocalizedMessage());
                        }
                    });
                    break;
                }
        }
    }

    private boolean isEmpty() {
        if (change_password.getText().toString().length() < 6) {
            Constant.showToastMessage(getApplicationContext(),
                    "Minimum 6 character required for password", 1);
            return false;
        }
        if (change_password.getText().toString().isEmpty() || change_repassword.getText().toString().isEmpty()) {
            Constant.showToastMessage(getApplicationContext(),
                    "Required all field", 1);
            return false;
        }
        if (!change_password.getText().toString().trim().equals(change_repassword.getText().toString().trim())) {
            Constant.showToastMessage(getApplicationContext(),
                    "Password not matched", 1);
            return false;
        }
        return true;
    }
}