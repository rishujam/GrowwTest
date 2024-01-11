package com.example.growwtest.data.repository

import android.util.Log
import com.example.growwtest.data.local.PeopleDao
import com.example.growwtest.data.local.PeopleDatabase
import com.example.growwtest.data.model.ResFilmListing
import com.example.growwtest.data.model.ResPeopleListing
import com.example.growwtest.data.remote.StarWarsApi
import com.example.growwtest.util.toResPeopleListing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 09/01/24.
 */

class StarWarsRepositoryImpl @Inject constructor(
    private val api: StarWarsApi,
    private val db: PeopleDatabase
) : StarWarsRepository {

    override suspend fun getPeople(page: Int): Flow<ResPeopleListing?> = flow {
        try {
            val key = "https://swapi.dev/api/people/?page=${page + 1}"
            val apiRes = api.getPeople(page)
            if(apiRes.isSuccessful) {
                apiRes.body()?.let {
                    db.dao.insertPeople(it.toEntityPeopleListing())
                    val cachedData = db.dao.getPeopleListing(key)
                    emit(cachedData?.toResPeopleListing())
                } ?: throw Exception("Null body")
            } else {
                val cacheData = db.dao.getPeopleListing(key)
                cacheData?.let {
                    emit(it.toResPeopleListing())
                }
            }
        } catch (e: Exception) {
            emit(null)
        }
    }

    override suspend fun getFilms(page: Int): Flow<ResFilmListing?> = flow {
        try {
            val apiRes = api.getFilms(page)
            if(apiRes.isSuccessful) {
                apiRes.body()?.let {
                    emit(it)
                } ?: throw Exception("Null body")
            } else {
                throw Exception(apiRes.message())
            }
        } catch (e: Exception) {
            Log.d("RishuTest", "error: ${e.message}")
            emit(null)
        }
    }
}