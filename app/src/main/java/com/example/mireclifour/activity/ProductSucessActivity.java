package com.example.mireclifour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mireclifour.R;
import com.example.mireclifour.utils.Constant;

public class ProductSucessActivity extends AppCompatActivity {

    TextView order_sucess_txt1, order_sucess_txt2, order_sucess_txt_3;
    Button sucess_btn;
    View vertical_line;
    ImageView order_face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sucess);

        sucess_btn = (Button) findViewById(R.id.sucess_btn);

        order_sucess_txt1 = (TextView) findViewById(R.id.order_sucess_txt1);
        vertical_line = (View) findViewById(R.id.vertical_line);
        order_face = (ImageView) findViewById(R.id.order_face);

        order_sucess_txt2 = (TextView) findViewById(R.id.order_sucess_txt2);
        order_sucess_txt_3 = (TextView) findViewById(R.id.order_sucess_txt_3);

        Intent sucess_it = getIntent();
        if (sucess_it.hasExtra("sucess_order")) {
            getordersucessdata();
        } else if (sucess_it.hasExtra("signup_sucess")) {
            signupsucess();
        }
    }

    private void signupsucess() {
        order_face.setImageResource(R.drawable.smiley);
        order_sucess_txt1.setText("YOUR MEMBERSHIP IS CREATED SUCCESSFULLY");
        order_sucess_txt1.setTypeface(Constant.font_bold(getApplicationContext()));
        order_sucess_txt2.setTypeface(Constant.font_bold(getApplicationContext()));
        sucess_btn.setText("Thank You");
        sucess_btn.setTypeface(Constant.font_bold(getApplicationContext()));

        sucess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductSucessActivity.this, MireclifourActivity.class));
            }
        });
    }

    private void getordersucessdata() {
        order_sucess_txt1.setTypeface(Constant.font_app(getApplicationContext()));
        order_sucess_txt_3.setText("Thank you for visiting and purchasing us! We are glad you found what you are looking for. It is our goal to be always happy about what you get from us, so let us know if your buying experience is perfect.");
        order_sucess_txt_3.setTypeface(Constant.font_app(getApplicationContext()));
        order_face.setImageResource(R.drawable.smiley);
        sucess_btn.setTypeface(Constant.font_bold(getApplicationContext()));
    }
}