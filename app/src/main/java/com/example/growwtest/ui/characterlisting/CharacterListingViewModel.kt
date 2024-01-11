package com.example.growwtest.ui.characterlisting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.Character
import com.example.growwtest.domain.model.CharacterFeature
import com.example.growwtest.domain.model.CharacterFeatures
import com.example.growwtest.domain.model.Characters
import com.example.growwtest.domain.usecase.GetCharacterFilterFeatures
import com.example.growwtest.domain.usecase.GetCharacterSortFeatures
import com.example.growwtest.domain.usecase.GetCharactersUseCase
import com.example.growwtest.domain.usecase.GetFilteredCharactersUseCase
import com.example.growwtest.ui.filter.model.FeatureAttr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 09/01/24.
 */

@HiltViewModel
class CharacterListingViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterFilterFeatures: GetCharacterFilterFeatures,
    private val getCharacterSortFeatures: GetCharacterSortFeatures,
    private val getFilteredCharactersUseCase: GetFilteredCharactersUseCase
) : ViewModel() {

    private val _characters = MutableSharedFlow<Resource<Characters>>()
    val characters = _characters.asSharedFlow()

    var allFilteringFeatures: CharacterFeatures? = null
    var selectedFilteringFeatures: CharacterFeatures? = null

    var nextPage: Int? = null
    private var isPeopleApiInProgress = false

    fun getCharacters() = viewModelScope.launch(Dispatchers.IO) {
        if(nextPage != -1 && !isPeopleApiInProgress) {
            isPeopleApiInProgress = true
            getCharactersUseCase.invoke(nextPage ?: 1).onEach {
                _characters.emit(it)
                if(it is Resource.Success || it is Resource.Error) {
                    isPeopleApiInProgress = false
                }
            }.launchIn(this)
        }
    }

    fun getFilteredCharacters(
        features: CharacterFeatures
    ) = viewModelScope.launch(Dispatchers.IO) {
        if(nextPage != -1 && !isPeopleApiInProgress) {
            isPeopleApiInProgress = true
            selectedFilteringFeatures = features
            getFilteredCharactersUseCase.invoke(nextPage ?: 1, features).onEach {
                _characters.emit(it)
                if(it is Resource.Success || it is Resource.Error) {
                    isPeopleApiInProgress = false
                }
            }.launchIn(this)
        }
    }

    fun getFilterFeatures(): CharacterFeatures {
        return allFilteringFeatures ?: run {
            val features = getCharacterFilterFeatures.invoke()
            allFilteringFeatures = features
            features
        }
    }

//    fun getSortFeatures(): CharacterFeatures {
//        return sortingFeatures ?: run {
//            val features = getCharacterSortFeatures.invoke()
//            sortingFeatures = features
//            features
//        }
//    }

}