package com.costrella.sp.sp.afternotif;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.costrella.sp.sp.MainActivity;
import com.costrella.sp.sp.R;
import com.costrella.sp.sp.model.Family;
import com.costrella.sp.sp.notify.NotifyController;

import io.realm.Realm;

public class AfterNotifyActivity extends AppCompatActivity {

    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_notify);
        btn = (Button) findViewById(R.id.button1af);
        textView = (TextView) findViewById(R.id.textView1);


        Realm realm =MainActivity.realm;
        Family family = realm.where(Family.class).equalTo("id", NotifyController.familyId).findFirst();

        textView.setText(family.getFamilyName());
    }
}
