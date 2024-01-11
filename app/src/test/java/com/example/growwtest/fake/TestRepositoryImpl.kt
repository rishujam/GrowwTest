package com.example.growwtest.fake

import com.example.growwtest.data.model.ResFilmListing
import com.example.growwtest.data.model.ResPeopleListing
import com.example.growwtest.data.repository.StarWarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
 * Created by Sudhanshu Kumar on 12/01/24.
 */

class TestRepositoryImpl : StarWarsRepository {

    override suspend fun getPeople(page: Int): Flow<ResPeopleListing?> = flow {
        if(page > 10) {
            emit(null)
        } else {
            emit(DataGenerator.getPeopleListingRes())
        }
    }

    override suspend fun getFilms(page: Int): Flow<ResFilmListing?> = flow {
        if(page > 2) {
            emit(null)
        } else {
            emit(DataGenerator.getFilmListingRes())
        }
    }
}