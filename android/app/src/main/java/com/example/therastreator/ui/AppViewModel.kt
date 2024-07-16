package com.example.therastreator.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.example.therastreator.data.ConfigRepository
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

lateinit var configRepository: ConfigRepository

class AppViewModel(context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val sendRepository = WorkManagerSendRepository(context)

    init {
        configRepository = ConfigRepository(context.dataStore)
        runBlocking {
            _uiState.value = AppUiState(activated = configRepository.isOn.first())
        }
    }

    fun changeActivated() {
        _uiState.update { currentState ->
            currentState.copy(activated = !_uiState.value.activated)
        }
        if(_uiState.value.activated) {
            sendRepository.start()
        } else {
            sendRepository.stop()
        }
        runBlocking {
            configRepository.savePreference(_uiState.value.activated)
        }
    }

}