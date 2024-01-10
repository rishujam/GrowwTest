package com.example.growwtest.domain.usecase

import android.util.Log
import com.example.growwtest.data.repository.StarWarsRepository
import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.Character
import com.example.growwtest.domain.model.Characters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class GetCharactersUseCase @Inject constructor(
    private val repository: StarWarsRepository
) {

    operator fun invoke(page: Int): Flow<Resource<Characters>> = flow {
        emit(Resource.Loading())
        repository.getPeople(page).collect {
            it?.let {
                val characters = it.results?.map { person ->
                    Character(
                        name = person.name,
                        gender = person.gender,
                        hairColor = person.hair_color,
                        skinColor = person.skin_color,
                        homeWorld = person.homeworld,
                        eyeColor = person.eye_color,
                        edited = person.edited,
                        created = person.created,
                        birthYear = person.birth_year
                    )
                }
                val nextPage = it.next?.last().toString().toIntOrNull()
                Log.d("RishuTest", "data for page usecase")
                emit(Resource.Success(Characters(nextPage, characters)))
            } ?: run {
                emit(Resource.Error("NULL"))
            }
        }
    }

}