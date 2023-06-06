package com.example.mireclifour.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mireclifour.R;


public class CodFragment extends Fragment {
    TextView txt_additional_data;

    public CodFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cod, container, false);
        txt_additional_data = (TextView) v.findViewById(R.id.txt_additional_data);
        return v;
    }
}