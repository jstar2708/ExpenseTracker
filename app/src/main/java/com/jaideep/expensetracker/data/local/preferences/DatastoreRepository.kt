package com.jaideep.expensetracker.data.local.preferences

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun getString(key: String): Flow<String?>
    suspend fun getInt(key: String): Flow<Int?>
    suspend fun clearDatastore()
}