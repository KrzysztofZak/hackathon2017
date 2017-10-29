package com.costrella.sp.sp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.costrella.sp.sp.model.Family;
import com.costrella.sp.sp.model.ItemsOfFamily;
import com.costrella.sp.sp.model.ItemsOfLeader;
import com.costrella.sp.sp.model.ItemsOfVolunteer;
import com.costrella.sp.sp.model.Leader;
import com.costrella.sp.sp.model.Volunteer;
import com.costrella.sp.sp.volunteer.VolunteerActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    public static boolean first;
    public static Realm realm;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu główne");
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VolunteerActivity.class);
                startActivity(intent);
            }
        });
        if (!first) {
            Realm.init(getApplicationContext());
            RealmConfiguration config = new RealmConfiguration
                    .Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }
        first = true;
//        Call<ItemsOfLeader> leaders = RetrofitInit.getInstance().getCechiniAPI().getLeaders();
//        leaders.enqueue(new Callback<ItemsOfLeader>() {
//            @Override
//            public void onResponse(Call<ItemsOfLeader> call, Response<ItemsOfLeader> response) {
//                if(response.code() == 200){
//                    realm.beginTransaction();
//                    for(Leader leader : response.body().getLeaders()){
//                        realm.insertOrUpdate(leader);
//                    }
//                    realm.commitTransaction();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ItemsOfLeader> call, Throwable t) {
//                Log.d("Failure", "failure");
//            }
//        });

        Family f1 = new Family();
        f1.setId(1);
        f1.setFamilyName("Kowalscy");
        f1.setAddress("ul. Dietla 25");

        Family f2 = new Family();
        f2.setId(2);
        f2.setFamilyName("Kalusowie");
        f2.setAddress("ul. Reymonta 16");

        Family f3 = new Family();
        f3.setId(3);
        f3.setFamilyName("Żakowie");
        f3.setAddress("ul. SLiczna 16");

        Family f4 = new Family();
        f4.setId(4);
        f4.setFamilyName("Jaworscy");
        f4.setAddress("ul. Zgody 16");

        realm.beginTransaction();
        realm.insertOrUpdate(f1);
        realm.insertOrUpdate(f2);
        realm.insertOrUpdate(f3);
        realm.insertOrUpdate(f4);
        realm.commitTransaction();

//        Call<ItemsOfFamily> familes = RetrofitInit.getInstance().getCechiniAPI().getFamilies();
//        familes.enqueue(new Callback<ItemsOfFamily>() {
//            @Override
//            public void onResponse(Call<ItemsOfFamily> call, Response<ItemsOfFamily> response) {
//                if(response.code() == 200){
//                    realm.beginTransaction();
//                    for(Family f : response.body().getFamilies()){
//                        realm.insertOrUpdate(f);
//                    }
//                    realm.commitTransaction();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ItemsOfFamily> call, Throwable t) {
//                Log.d("Failure", "failure");
//            }
//        });

//        Call<ItemsOfVolunteer> volunteers = RetrofitInit.getInstance().getCechiniAPI().getVolunteers();
//        volunteers.enqueue(new Callback<ItemsOfVolunteer>() {
//            @Override
//            public void onResponse(Call<ItemsOfVolunteer> call, Response<ItemsOfVolunteer> response) {
//                if(response.code() == 200){
//                    realm.beginTransaction();
//                    for(Volunteer v : response.body().getVolunteers()){
//                        realm.insertOrUpdate(v);
//                    }
//                    realm.commitTransaction();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ItemsOfVolunteer> call, Throwable t) {
//                Log.d("Failure", "failure");
//            }
//        });

    }


}
