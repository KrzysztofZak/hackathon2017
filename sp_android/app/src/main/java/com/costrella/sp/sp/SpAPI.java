package com.costrella.sp.sp;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mike on 2017-10-28.
 */
public interface SpAPI {
    String ENDPOINT = "https://raw.githubusercontent.com/costrella/hackathon2017/master/";

    @GET("exampleGlobalPositions.json")
    Call<Positions> getFamily();
}
