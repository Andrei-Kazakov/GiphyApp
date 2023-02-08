package com.example.giphyapp.data

import retrofit2.Call
import retrofit2.http.GET

const val API_KEY = "oPaJ1YVW11CUhVSO0ZPn0qV62qxbPNdt"

interface DataService {

    @GET("gifs/trending?api_key=$API_KEY")
    fun getGifs(): Call<GifsListResponse>
}