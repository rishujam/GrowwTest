package com.example.growwtest.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.Characters
import com.example.growwtest.fake.TestRepositoryImpl
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

/*
 * Created by Sudhanshu Kumar on 12/01/24.
 */

class GetCharactersUseCaseTest {

    private val useCase = GetCharactersUseCase(TestRepositoryImpl())

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `when null data from repo, return error` () = runTest {
        val events = mutableListOf<Resource<Characters>>()
        useCase.invoke(12).collect {
            events.add(it)
        }
        if(events.getOrNull(0) is Resource.Loading && events.getOrNull(1) is Resource.Error) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun `when valid data from repo, return success` () = runTest {
        val events = mutableListOf<Resource<Characters>>()
        useCase.invoke(2).collect {
            events.add(it)
        }
        if(events.getOrNull(0) is Resource.Loading && events.getOrNull(1) is Resource.Success) {
            assert(true)
        } else {
            assert(false)
        }
    }

}