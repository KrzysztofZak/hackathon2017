package com.costrella.sp.sp.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.costrella.sp.sp.MapsActivity;
import com.costrella.sp.sp.R;
import com.costrella.sp.sp.family.FamilyActivity;

public class VolunteerActivity extends AppCompatActivity {

    Button button_vol1;
    Button button_vol2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Panel wolontariusza");
        setContentView(R.layout.activity_volunteer);
        button_vol1 = (Button) findViewById(R.id.button_vol1);
        button_vol2 = (Button) findViewById(R.id.button_vol2);
        button_vol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        button_vol2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FamilyActivity.class);
                startActivity(intent);
            }
        });
    }
}
