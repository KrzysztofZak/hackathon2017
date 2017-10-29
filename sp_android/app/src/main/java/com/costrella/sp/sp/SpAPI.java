package com.costrella.sp.sp;

import com.costrella.sp.sp.model.ItemsOfFamily;
import com.costrella.sp.sp.model.ItemsOfLeader;
import com.costrella.sp.sp.model.ItemsOfVolunteer;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mike on 2017-10-28.
 */
public interface SpAPI {
    String ENDPOINT = "https://raw.githubusercontent.com/costrella/hackathon2017/master/";

    @GET("exampleGlobalPositions.json")
    Call<Positions> getFamily();

    @GET("exampleGlobalPositions.json")
    Call<ItemsOfFamily> getFamilies();

    @GET("exampleGlobalPositions.json")
    Call<ItemsOfVolunteer> getVolunteers();

    @GET("exampleGlobalPositions.json")
    Call<ItemsOfLeader> getLeaders();
}
