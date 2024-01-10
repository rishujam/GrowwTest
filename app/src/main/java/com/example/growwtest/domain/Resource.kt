package com.example.growwtest.domain

/*
 * Created by Sudhanshu Kumar on 23/12/23.
 */

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Error<T>(message: String?) : Resource<T>(message = message)
    class Success<T>(data: T) : Resource<T>(data = data)
    class Loading<T> : Resource<T>(null, null)
}
