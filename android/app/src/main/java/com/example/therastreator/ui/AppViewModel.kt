package com.example.therastreator.ui

import android.content.Context
import androidx.compose.runtime.currentCompositionErrors
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.example.therastreator.configRepository
import com.example.therastreator.data.AppUiState
import com.example.therastreator.data.WorkManagerSendRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

private const val CONFIG_NAME = "user_config"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = CONFIG_NAME
)

class AppViewModel(context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val sendRepository = WorkManagerSendRepository(context)

    fun readConfig() {
        runBlocking {
            _uiState.update { currentState ->

                return@update AppUiState()
            }
        }
    }

}