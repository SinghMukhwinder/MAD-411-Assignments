package com.zybooks.assignment06.network

import retrofit2.http.GET

interface CurrencyApiService{
    @GET("currencies.json")
    suspend fun getPriceCode(): Map<String, String>


}