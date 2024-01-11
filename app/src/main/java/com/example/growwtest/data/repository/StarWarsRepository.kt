package com.example.growwtest.data.repository

import com.example.growwtest.data.model.ResFilmListing
import com.example.growwtest.data.model.ResPeopleListing
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/*
 * Created by Sudhanshu Kumar on 09/01/24.
 */

interface StarWarsRepository {

    suspend fun getPeople(page: Int): Flow<ResPeopleListing?>

    suspend fun getFilms(page: Int): Flow<ResFilmListing?>

}