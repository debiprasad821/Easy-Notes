package com.example.easynotes.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStore: DataStore<Preferences>) {
    companion object {
        var IS_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
    }

    fun getIsLoggedIn(): Flow<Boolean> {
        return dataStore.data.catch { emit(emptyPreferences()) }.map {
            it[IS_LOGGED_IN] ?: false
        }
    }

    suspend fun setIsLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit {
            it[IS_LOGGED_IN] = isLoggedIn
        }
    }
}