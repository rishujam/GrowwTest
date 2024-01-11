package com.example.growwtest.data.model


data class ResFilmListing(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Film>
)