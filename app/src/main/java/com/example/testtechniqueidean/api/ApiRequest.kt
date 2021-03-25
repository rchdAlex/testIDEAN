package com.example.testtechniqueidean.api

import retrofit2.Call
import retrofit2.http.GET

const val Base_URL ="https://ghibliapi.herokuapp.com"

interface ApiRequest {

    @GET("/films")
    fun getFilmGhibli() : Call<List<ApiData>>
}