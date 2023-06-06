package com.example.mireclifour.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mireclifour.R;


public class Constant {

    public static String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static String name = "";
    public static Typeface typeface;
    public static final String MAIN_PREF_NAME = "mireclifour";

    public static Typeface font_app(Context context) {
        typeface = ResourcesCompat.getFont(context, R.font.oswaldlight);
        return typeface;
    }

    public static Typeface font_light(Context context) {
        typeface = ResourcesCompat.getFont(context, R.font.oswaldextralight);
        return typeface;
    }

    public static Typeface font_medium(Context context) {
        typeface = ResourcesCompat.getFont(context, R.font.oswaldregular);
        return typeface;
    }

    public static Typeface font_bold(Context context) {
        typeface = ResourcesCompat.getFont(context, R.font.oswaldbold);
        return typeface;
    }

    public static void showToastMessage(Context mcontext, String string, int type) {
        if (mcontext != null) {

            View layout = LayoutInflater.from(mcontext).inflate(
                    R.layout.toastmsg, null);

            TextView txt_msg = (TextView) layout.findViewById(R.id.txt_msg);
            LinearLayout ll_border = (LinearLayout) layout
                    .findViewById(R.id.ll_border);

            if (type == 1) {
                txt_msg.setBackgroundDrawable(mcontext.getResources().getDrawable(
                        R.drawable.error_toast_msg));
                txt_msg.setTextColor(mcontext.getResources().getColor(R.color.red));
                ll_border.setBackgroundColor(mcontext.getResources().getColor(
                        R.color.red_transparent));
            } else if (type == 2) {
                txt_msg.setBackgroundDrawable(mcontext.getResources().getDrawable(
                        R.drawable.sucess_toast_msg));
                txt_msg.setTextColor(mcontext.getResources().getColor(R.color.green));
                ll_border.setBackgroundColor(mcontext.getResources().getColor(
                        R.color.green_transparent));
            }

            txt_msg.setText(string);

            Toast toast = new Toast(mcontext);
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }
}