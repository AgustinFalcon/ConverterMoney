package com.example.convertermoney.main

import com.example.convertermoney.data.models.CurrencyResponse
import com.example.convertermoney.util.Resource

interface MainRepository {

    suspend fun getRates(base: String) : Resource<CurrencyResponse>

}