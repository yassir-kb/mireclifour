package com.example.mireclifour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mireclifour.R;
import com.onesignal.OneSignal;

public class SplashActivity extends AppCompatActivity {
    public static String variables_str, price_symbol_val, api_key;
    private static SplashActivity mInstance;

    public static synchronized SplashActivity getInstance() {
        return mInstance;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
            }
        });
        variabledata();
    }

    private void variabledata() {
        variables_str = "Loading...";
        price_symbol_val = " " + "€";
        api_key = "€";
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}