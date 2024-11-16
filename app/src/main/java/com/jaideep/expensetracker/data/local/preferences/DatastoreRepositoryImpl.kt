package com.jaideep.expensetracker.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : DatastoreRepository {
    override suspend fun putString(key: String, value: String) {
        datastore.edit { mutablePreferences ->
            val datastoreKey = stringPreferencesKey(key)
            mutablePreferences[datastoreKey] = value
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        datastore.edit { mutablePreferences ->
            val datastoreKey = intPreferencesKey(key)
            mutablePreferences[datastoreKey] = value
        }
    }

    override suspend fun getString(key: String): Flow<String?> {
        val datastoreKey = stringPreferencesKey(key)
        return datastore.data.map { preferences ->
            preferences[datastoreKey]
        }
    }

    override suspend fun getInt(key: String): Flow<Int?> {
        val datastoreKey = intPreferencesKey(key)
        return datastore.data.map { preferences ->
            preferences[datastoreKey]
        }
    }

    override suspend fun clearDatastore() {
        datastore.edit { mutablePreferences ->
            mutablePreferences.clear()
        }
    }
}