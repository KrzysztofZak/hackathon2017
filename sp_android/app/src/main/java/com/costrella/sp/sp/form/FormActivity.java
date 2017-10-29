package com.costrella.sp.sp.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.costrella.sp.sp.R;


public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        setTitle("Wypelnij formularz");
    }
}
