package com.example.growwtest.data.di

import android.app.Application
import androidx.room.Room
import com.example.growwtest.data.local.Converters
import com.example.growwtest.data.local.PeopleDao
import com.example.growwtest.data.local.PeopleDatabase
import com.example.growwtest.data.parser.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): PeopleDatabase {
        return Room.databaseBuilder(
            app, PeopleDatabase::class.java, "people_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

}