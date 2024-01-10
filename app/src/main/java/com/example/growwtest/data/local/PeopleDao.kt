package com.example.growwtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.growwtest.data.model.EntityPeopleListing

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@Dao
interface PeopleDao {

    @Query("SELECT * FROM entitypeoplelisting WHERE next = :nextValue")
    fun getPeopleListing(nextValue: String): EntityPeopleListing?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeople(people: EntityPeopleListing)

}