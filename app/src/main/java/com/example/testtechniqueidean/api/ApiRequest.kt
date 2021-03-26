package com.example.testtechniqueidean.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

const val Base_URL ="https://ghibliapi.herokuapp.com"

interface ApiRequest {

    @GET("/films")
    fun getFilmGhibli() : Observable<List<ApiData>> //fun getFilmGhibli() : Call<List<ApiData>>
}