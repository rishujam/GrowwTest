package com.example.growwtest.ui.characterlisting

import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.Characters
import com.example.growwtest.domain.usecase.GetCharacterFilterFeatures
import com.example.growwtest.domain.usecase.GetCharacterSortFeatures
import com.example.growwtest.domain.usecase.GetCharactersUseCase
import com.example.growwtest.domain.usecase.GetFilteredCharactersUseCase
import com.example.growwtest.fake.TestRepositoryImpl
import io.mockk.coEvery
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/*
 * Created by Sudhanshu Kumar on 12/01/24.
 */

class CharacterListingViewModelTest {

    private lateinit var viewModel: CharacterListingViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val repo = TestRepositoryImpl()
        val getCharactersUseCase = GetCharactersUseCase(repo)
        val getFilteredCharactersUseCase = GetFilteredCharactersUseCase(repo)
        val getCharacterFiltersFeatures = GetCharacterFilterFeatures()
        val getCharacterSortFeatures = GetCharacterSortFeatures()
        viewModel = CharacterListingViewModel(
            getCharactersUseCase,
            getCharacterFiltersFeatures,
            getCharacterSortFeatures,
            getFilteredCharactersUseCase
        )
    }

    @Test
    fun `when valid data, emit success` () = runTest {
        val events = mutableListOf<Resource<Characters>>()
        val job = launch {
            viewModel.characters.collect {
                events.add(it)
            }
        }
        coEvery { viewModel.getCharacters() }
        job.join()
        if(events.getOrNull(0) is Resource.Loading && events.getOrNull(1) is Resource.Success) {
            assert(true)
        } else {
            assert(false)
        }
        job.cancel()
    }

    @Test
    fun `when invalid data, emit error` () = runTest {

    }

    @Test
    fun `when success characters response, update nextPage` () = runTest {

    }


}