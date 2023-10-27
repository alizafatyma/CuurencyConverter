package com.example.currencyconverter;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ExchangeRate
{
    @SerializedName("rates")
    private Map<String, Double> rate;

    public Map<String, Double> getRates()
    {
        return rate;
    };

}
