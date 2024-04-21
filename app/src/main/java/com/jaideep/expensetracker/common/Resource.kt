package com.example.expensetracker.common

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>()
    class Error<T>(message: String) : Resource<T>()
}