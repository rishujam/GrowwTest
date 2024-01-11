package com.example.growwtest.ui.filmlisting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.FilmUI
import com.example.growwtest.domain.usecase.GetFilmsByCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

@HiltViewModel
class FilmListingViewModel(
    private val getFilmsByCharacterUseCase: GetFilmsByCharacterUseCase
) : ViewModel() {

    private val _films = MutableSharedFlow<Resource<List<FilmUI>>>()
    val films = _films.asSharedFlow()

    var nextPage: Int? = null
    private var isPeopleApiInProgress = false

    fun getFilms(character: String) = viewModelScope.launch(Dispatchers.IO) {
        if(nextPage != -1 && !isPeopleApiInProgress) {
            isPeopleApiInProgress = true
            getFilmsByCharacterUseCase.invoke(nextPage ?: 1, character).onEach {
                _films.emit(it)
                if(it is Resource.Success || it is Resource.Error) {
                    isPeopleApiInProgress = false
                }
            }.launchIn(this)
        }

    }

}