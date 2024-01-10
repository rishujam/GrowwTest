package com.example.growwtest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

@Entity
data class EntityPeopleListing(
    val count: Int? = null,
    @PrimaryKey
    val next: String,
    val previous: String? = null,
    val results: List<Person>? = null
)
