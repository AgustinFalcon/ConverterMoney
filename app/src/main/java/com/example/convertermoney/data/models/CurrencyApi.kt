package com.example.convertermoney.data.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest?access_key=467b1a76fc6b9bdf77f730da7fdbf2fb")
    suspend fun getRates(@Query("base")base: String) : Response<CurrencyResponse>

}