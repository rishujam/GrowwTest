package com.example.growwtest.fake

import com.example.growwtest.data.model.Film
import com.example.growwtest.data.model.Person
import com.example.growwtest.data.model.ResFilmListing
import com.example.growwtest.data.model.ResPeopleListing

/*
 * Created by Sudhanshu Kumar on 12/01/24.
 */

object DataGenerator {

    fun getPeopleListingRes(): ResPeopleListing {
        return ResPeopleListing(
            count = 2,
            next = "https://swapi.dev/api/people/?page=2",
            previous = null,
            results = listOf(
                Person(
                    "sfh",
                    "sfs",
                    "sfs",
                    "sfs",
                    listOf("sfs"),
                    "sfs",
                    "sfs",
                    "sfs",
                    "sfs",
                    "sfs",
                    "sfs",
                    "sfs",
                    listOf("sfs"),
                    listOf("sfs"),
                    "sfs",
                    listOf("sfs")
                )
            )
        )
    }

    fun getFilmListingRes(): ResFilmListing {
        return ResFilmListing(
            count = 2,
            next = "https://swapi.dev/api/film/?page=2",
            previous = null,
            results = listOf(
                Film(
                    listOf("sfh"),
                    "sfs",
                    "sfs",
                    "sfs",
                    1,
                    "sfs",
                    listOf("sfs"),
                    "sfs",
                    "sfs",
                    listOf("sfs"),
                    listOf("sfs"),
                    "sfs",
                    "sfs",
                    listOf("sfs")
                )
            )
        )
    }

}