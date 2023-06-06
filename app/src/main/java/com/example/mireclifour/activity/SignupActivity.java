package com.example.mireclifour.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.model.User;
import com.example.mireclifour.utils.Constant;
import com.example.mireclifour.utils.UserService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    int genid, typid;
    Calendar c;
    DatePickerDialog dpd;
    int mYear, mMonth, mDay;
    String qoute_id = "";
    String gender = "", type = "";
    Button btn_sign_up;
    ImageView signup_back_img;
    TextInputLayout input_layout_firstname, input_layout_lastname, input_layout_mail, input_layout_logo, input_layout_password, input_layout_repassword, input_layout_number;
    TextView text, sign_up_date, txt_gender, txt_type, dob;
    EditText sign_up_firstname, sign_up_lastname, sign_up_mail, sign_up_logo, sign_up_password, sign_up_repassword, sign_up_number;
    RadioGroup sign_up_gender, sign_up_type;
    RadioButton sign_up_male, sign_up_female, sign_up_supplier, sign_up_customer, radioButton1, radioButton2;
    CheckBox chk_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constant.MAIN_PREF_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("quote_id", null);

        if (restoredText != null) {
            qoute_id = prefs.getString("quote_id", "No name defined");
            Log.e("Login_quote_id", qoute_id);
        }

        getcast();
    }

    public void getcast() {

        input_layout_firstname = (TextInputLayout) findViewById(R.id.input_layout_name);
        input_layout_firstname.setHint("First Name");
        input_layout_firstname.setTypeface(Constant.font_medium(this));

        input_layout_lastname = (TextInputLayout) findViewById(R.id.input_layout_lastname);
        input_layout_lastname.setTypeface(Constant.font_medium(this));
        input_layout_lastname.setHint("Last Name");

        input_layout_mail = (TextInputLayout) findViewById(R.id.input_layout_mail);
        input_layout_mail.setHint("E-mail");
        input_layout_mail.setTypeface(Constant.font_medium(this));

        input_layout_number = (TextInputLayout) findViewById(R.id.input_layout_number);
        input_layout_number.setHint("Mobile");
        input_layout_number.setTypeface(Constant.font_medium(this));

        dob = (TextView) findViewById(R.id.dob);
        dob.setText("Date of Birth");
        dob.setTypeface(Constant.font_app(this));
        sign_up_number = (EditText) findViewById(R.id.sign_up_number);

        txt_gender = (TextView) findViewById(R.id.txt_gender);
        txt_gender.setText("Gender");
        txt_gender.setTypeface(Constant.font_app(this));
        signup_back_img = (ImageView) findViewById(R.id.signup_back_img);

        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_type.setText("Mireclifour Account");
        txt_type.setTypeface(Constant.font_app(this));

        input_layout_logo = (TextInputLayout) findViewById(R.id.input_layout_logo);
        input_layout_logo.setHint("URL for Logo (If you're a supplier)");
        input_layout_logo.setTypeface(Constant.font_medium(this));

        input_layout_password = (TextInputLayout) findViewById(R.id.input_layout_password);
        input_layout_password.setHint("Password");
        input_layout_password.setTypeface(Constant.font_medium(this));

        input_layout_repassword = (TextInputLayout) findViewById(R.id.input_layout_repassword);
        input_layout_repassword.setHint("Re-Password");
        input_layout_repassword.setTypeface(Constant.font_medium(this));

        sign_up_firstname = (EditText) findViewById(R.id.sign_up_firstname);
        sign_up_firstname.setTypeface(Constant.font_app(this));

        sign_up_lastname = (EditText) findViewById(R.id.sign_up_lastname);
        sign_up_lastname.setTypeface(Constant.font_app(this));

        sign_up_mail = (EditText) findViewById(R.id.sign_up_mail);
        sign_up_mail.setTypeface(Constant.font_app(this));

        sign_up_number.setTypeface(Constant.font_app(this));

        sign_up_date = (TextView) findViewById(R.id.sign_up_date);
        sign_up_date.setText("Date of Birth");

        sign_up_male = (RadioButton) findViewById(R.id.sign_up_male);
        sign_up_male.setText("Men");
        sign_up_male.setTypeface(Constant.font_app(this));

        sign_up_female = (RadioButton) findViewById(R.id.sign_up_female);
        sign_up_female.setText("Women");
        sign_up_female.setTypeface(Constant.font_app(this));

        sign_up_gender = (RadioGroup) findViewById(R.id.sign_up_gender);

        sign_up_supplier = (RadioButton) findViewById(R.id.sign_up_supplier);
        sign_up_supplier.setText("Supplier");
        sign_up_supplier.setTypeface(Constant.font_app(this));

        sign_up_customer = (RadioButton) findViewById(R.id.sign_up_customer);
        sign_up_customer.setText("Customer");
        sign_up_customer.setTypeface(Constant.font_app(this));

        sign_up_type = (RadioGroup) findViewById(R.id.sign_up_type);

        sign_up_logo = (EditText) findViewById(R.id.sign_up_logo);
        sign_up_logo.setTypeface(Constant.font_app(this));

        sign_up_password = (EditText) findViewById(R.id.sign_up_password);
        sign_up_password.setTypeface(Constant.font_app(this));

        sign_up_repassword = (EditText) findViewById(R.id.sign_up_repassword);
        sign_up_repassword.setTypeface(Constant.font_app(this));

        text = (TextView) findViewById(R.id.text);
        chk_signup = (CheckBox) findViewById(R.id.chk_signup);

        sign_up_number.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        sign_up_firstname.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        sign_up_lastname.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        sign_up_password.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        sign_up_repassword.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);
        btn_sign_up.setText("Sign up");
        btn_sign_up.setOnClickListener(this);
        sign_up_date.setOnClickListener(this);

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

            //BEGIN DATE CLICK//

            case R.id.sign_up_date: {
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        sign_up_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                c.add(Calendar.YEAR, -110);
                dpd.getDatePicker().setMinDate(c.getTimeInMillis());
                c.add(Calendar.YEAR, 110);
                dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dpd.show();
                break;
            }

            case R.id.btn_sign_up: {
                if (isEmpty()) {
                    genid = sign_up_gender.getCheckedRadioButtonId();
                    radioButton1 = (RadioButton) findViewById(genid);
                    gender = radioButton1.getText().toString();

                    typid = sign_up_type.getCheckedRadioButtonId();
                    radioButton2 = (RadioButton) findViewById(typid);
                    type = radioButton2.getText().toString();

                    User user = new User(
                            sign_up_firstname.getText().toString(),
                            sign_up_lastname.getText().toString(),
                            sign_up_mail.getText().toString(),
                            sign_up_number.getText().toString(),
                            sign_up_date.getText().toString(),
                            gender,
                            type,
                            sign_up_logo.getText().toString(),
                            sign_up_password.getText().toString()
                    );

                    postApi(user);
                    Intent sucess_it = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(sucess_it);
                    break;
                }
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

    private void postApi(User user) {

        UserService service = getRetrofit().create(UserService.class);
        Call<Void> call = service.postUser(user);

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

    private boolean isEmpty() {

        if (sign_up_firstname.getText().toString().trim().isEmpty() || sign_up_lastname.getText().toString().trim().isEmpty() || sign_up_mail.getText().toString().trim().isEmpty()
                || sign_up_password.getText().toString().isEmpty() || sign_up_repassword.getText().toString().isEmpty() || sign_up_date.getText().toString().isEmpty() || sign_up_number.getText().toString().isEmpty()) {
            Constant.showToastMessage(getApplicationContext(),
                    "Required all field.", 1);
            return false;
        }

        if (!sign_up_mail.getText().toString().trim().matches(Constant.email_validate)) {
            Constant.showToastMessage(getApplicationContext(),
                    "Invalid mail.", 1);

            return false;
        }
        if (sign_up_number.getText().toString().length() < 11) {
            Constant.showToastMessage(getApplicationContext(),
                    "Please enter 11 digit in telephone", 1);

            return false;
        }
        if (!chk_signup.isChecked()) {
            Constant.showToastMessage(getApplicationContext(),
                    "Please confirm", 1);

            return false;
        }

        if (sign_up_password.getText().toString().length() < 6) {
            Constant.showToastMessage(getApplicationContext(),
                    "Minimum 6 character required for password.", 1);

            return false;
        }


        if (!sign_up_password.getText().toString().trim().equals(sign_up_repassword.getText().toString().trim())) {
            Constant.showToastMessage(getApplicationContext(),
                    "Password not matched.", 1);

            return false;
        }

        return true;
    }

}