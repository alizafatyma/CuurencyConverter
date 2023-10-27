package com.example.currencyconverter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RateApi
{
    @GET("latest.json")
    Call<ExchangeRate> getExchangeRates(
            @Query("api") String key
            );

}
