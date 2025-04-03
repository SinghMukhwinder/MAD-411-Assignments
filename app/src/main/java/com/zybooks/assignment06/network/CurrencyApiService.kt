package com.zybooks.assignment06.network

import com.zybooks.assignment06.Cost
import retrofit2.http.GET

interface CurrencyApiService{
    @GET("currencies.json")
    suspend fun getCountryCode(): Map<String, String>


    @GET("currencies/cad.json")
    suspend fun getPrice(): Cost
}