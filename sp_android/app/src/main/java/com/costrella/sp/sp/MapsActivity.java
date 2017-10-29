package com.costrella.sp.sp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.costrella.sp.sp.model.Family;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("Mapa");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getFamily() {
        Call<Positions> call = RetrofitInit.getInstance().getCechiniAPI().getFamily();
        call.enqueue(new Callback<Positions>() {
            @Override
            public void onResponse(Call<Positions> call, Response<Positions> response) {
                if(response.code() == 200){
                    Positions positions = response.body();
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for(Family family : positions.getPositions()){
                        builder.include(new LatLng(family.getLatitude(), family.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(
                                new LatLng(family.getLatitude(), family.getLongitude())).title(family.getFamilyName()));
                    }
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
                }
            }

            @Override
            public void onFailure(Call<Positions> call, Throwable t) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getFamily();
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String lat = String.valueOf(marker.getPosition().latitude);
                String lon = String.valueOf(marker.getPosition().longitude);
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+lat+","+lon);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });
        // Add a marker in Sydney and move the camera
//        LatLng krk = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(krk).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(krk));
    }
}
