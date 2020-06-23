package com.burakkarakoca.koronagncelveriler.service

import com.burakkarakoca.koronagncelveriler.model.CovidResponse
import retrofit2.Call
import retrofit2.http.GET

interface CovidAPI {

    // https://pomber.github.io/ -- BASE
    // covid19/timeseries.json -- ROOT

    @GET("covid19/timeseries.json")
    fun getCovidData(): Call<CovidResponse>

}