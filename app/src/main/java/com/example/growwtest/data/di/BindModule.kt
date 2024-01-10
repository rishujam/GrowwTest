package com.example.growwtest.data.di

import com.example.growwtest.data.repository.StarWarsRepository
import com.example.growwtest.data.repository.StarWarsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    @Singleton
    abstract fun bindStarWarRepo(impl: StarWarsRepositoryImpl): StarWarsRepository

}