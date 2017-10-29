package com.costrella.sp.sp.family;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.costrella.sp.sp.MainActivity;
import com.costrella.sp.sp.R;
import com.costrella.sp.sp.model.Family;

import java.util.List;

import io.realm.Realm;

public class FamilyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Panel wolontariusza");
        setContentView(R.layout.activity_family);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Realm realm = MainActivity.realm;
        List<Family> families = realm.where(Family.class).findAll();


        FamilyAdapter mAdapter = new FamilyAdapter(families);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
