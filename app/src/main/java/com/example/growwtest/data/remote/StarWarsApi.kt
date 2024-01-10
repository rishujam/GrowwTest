package com.example.growwtest.data.remote

import com.example.growwtest.data.model.ResFilmListing
import com.example.growwtest.data.model.ResPeopleListing
import com.example.growwtest.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Created by Sudhanshu Kumar on 09/01/24.
 */

interface StarWarsApi {

    @GET("people/")
    suspend fun getPeople(@Query("page") page: Int): Response<ResPeopleListing>

    @GET("films/")
    suspend fun getFilms(@Query("page") page: Int): Response<ResFilmListing>

    companion object {
        private val client = OkHttpClient.Builder().build()
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api: StarWarsApi by lazy {
            retrofit.create(StarWarsApi::class.java)
        }
    }

}