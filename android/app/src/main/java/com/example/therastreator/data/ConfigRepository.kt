package com.example.therastreator.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ConfigRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val IS_ON = booleanPreferencesKey("is_on")
        const val TAG = "configRepo"
    }

    val isOn: Flow<Boolean> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
        preferences[IS_ON] ?: false
    }

    suspend fun savePreference(isOn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_ON] = isOn

        }
    }

}