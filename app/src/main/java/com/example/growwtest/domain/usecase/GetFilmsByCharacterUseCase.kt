package com.example.growwtest.domain.usecase

import com.example.growwtest.data.repository.StarWarsRepositoryImpl
import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.FilmUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

class GetFilmsByCharacterUseCase @Inject constructor(
    private val repository: StarWarsRepositoryImpl
) {

    operator fun invoke(page: Int, character: String): Flow<Resource<List<FilmUI>>> = flow {
        emit(Resource.Loading())
        repository.getFilms(page).collect {
            it?.let { res ->
                val films = mutableListOf<FilmUI>()
                for(film in res.results) {
                    if(film.characters.contains(character)) {
                        val filmUi = FilmUI(
                            character,
                            film.episode_id,
                            film.producer,
                            film.release_date,
                            film.title
                        )
                        films.add(filmUi)
                    }
                }
                emit(Resource.Success(films))
            } ?: run {
                emit(Resource.Error("NULL"))
            }
        }
    }

}