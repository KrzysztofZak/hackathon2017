package com.costrella.sp.sp.model;

import io.realm.RealmObject;

/**
 * Created by mike on 2017-10-28.
 */
public class Family extends RealmObject {

    String FamilyName;
    double Latitude;
    double Longitude;

    public String getFamilyName() {
        return FamilyName;
    }

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
