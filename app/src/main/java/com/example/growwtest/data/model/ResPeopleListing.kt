package com.example.growwtest.data.model

data class ResPeopleListing(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Person>?
) {
    fun toEntityPeopleListing(): EntityPeopleListing {
        return EntityPeopleListing(
            count = count,
            next = next ?: "null",
            previous = previous,
            results = results
        )
    }
}