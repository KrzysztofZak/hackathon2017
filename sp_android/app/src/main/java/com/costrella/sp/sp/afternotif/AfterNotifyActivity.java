package com.costrella.sp.sp.afternotif;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.costrella.sp.sp.MainActivity;
import com.costrella.sp.sp.R;
import com.costrella.sp.sp.family.FamilyActivity;
import com.costrella.sp.sp.model.Family;
import com.costrella.sp.sp.notify.NotifyController;

import io.realm.Realm;

public class AfterNotifyActivity extends AppCompatActivity {

    Button btn, btngo;
    TextView textView, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_notify);
        btn = (Button) findViewById(R.id.button1af);
        btngo= (Button) findViewById(R.id.btngo);
        textView = (TextView) findViewById(R.id.textView1);
        address = (TextView) findViewById(R.id.address);


        Realm realm =MainActivity.realm;
        Family family = realm.where(Family.class).equalTo("id", NotifyController.familyId).findFirst();
        setTitle(family.getFamilyName());
        textView.setText(family.getFamilyName());
        address.setText(family.getAddress());

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FamilyActivity.class));
            }
        });
    }
}
