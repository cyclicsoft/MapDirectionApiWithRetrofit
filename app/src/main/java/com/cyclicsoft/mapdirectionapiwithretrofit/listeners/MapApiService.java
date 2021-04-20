package com.cyclicsoft.mapdirectionapiwithretrofit.listeners;

import com.cyclicsoft.mapdirectionapiwithretrofit.models.DirectionResponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapApiService {
    @GET("maps/api/directions/json?sensor=false&mode=walking")
    Call<DirectionResponses> getDirection(@Query("origin") String origin,
                                          @Query("destination") String destination,
                                          @Query("key") String apiKey);
}
