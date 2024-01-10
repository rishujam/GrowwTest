package com.example.growwtest.data.model


data class ResFilmListing(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Film>
)