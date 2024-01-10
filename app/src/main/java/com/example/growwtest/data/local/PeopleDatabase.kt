package com.example.growwtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.growwtest.data.model.EntityPeopleListing
import com.example.growwtest.data.model.ResPeopleListing

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@Database(
    entities = [EntityPeopleListing::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PeopleDatabase : RoomDatabase() {

    abstract val dao: PeopleDao

}