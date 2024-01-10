package com.example.growwtest.domain.usecase

import com.example.growwtest.data.repository.StarWarsRepository
import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.Character
import com.example.growwtest.domain.model.CharacterFeature
import com.example.growwtest.domain.model.CharacterFeatures
import com.example.growwtest.domain.model.Characters
import com.example.growwtest.ui.filter.model.FeatureAttr
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class GetFilteredCharactersUseCase @Inject constructor(
    private val repository: StarWarsRepository
) {

    operator fun invoke(
        page: Int,
        characterFeatures: CharacterFeatures
    ): Flow<Resource<Characters>> = flow {
        emit(Resource.Loading())
        repository.getPeople(page).collect {
            it?.results?.let { res ->
                val characters = res.filter { character ->
                    for(i in characterFeatures.features) {
                        when(i) {
                            is CharacterFeature.EyeColor -> {
                                if(i.attrs.contains(FeatureAttr(character.eye_color, true))) {
                                    return@filter true
                                }
                            }
                            is CharacterFeature.Gender -> {
                                if(i.attrs.contains(FeatureAttr(character.gender, true))) {
                                    return@filter true
                                }
                            }
                            is CharacterFeature.HairColor -> {
                                if(i.attrs.contains(FeatureAttr(character.hair_color, true))) {
                                    return@filter true
                                }
                            }
                            is CharacterFeature.SkinColor -> {
                                if(i.attrs.contains(FeatureAttr(character.skin_color, true))) {
                                    return@filter true
                                }
                            }
                            else -> {}
                        }
                    }
                    false
                }.map { person ->
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
                emit(Resource.Success(Characters(nextPage, characters)))
            }
        }

    }

}