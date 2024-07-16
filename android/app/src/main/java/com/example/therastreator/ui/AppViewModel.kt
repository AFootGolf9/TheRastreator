package com.example.therastreator.ui

import android.content.Context
import android.util.Log
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

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private lateinit var sendRepository: WorkManagerSendRepository

    fun doFirst(context: Context) {
        configRepository = ConfigRepository(context.dataStore)
        runBlocking {
            _uiState.update { currentState ->
                currentState.copy(activated = configRepository.isOn.first())
            }
        }
        sendRepository = WorkManagerSendRepository(context)
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

    fun changeUser(user: String) {
        _uiState.update { currentState ->
            currentState.copy(user = user)
        }
    }

    fun changePass(pass: String) {
        _uiState.update { currentState ->
            currentState.copy(pass = pass)
        }
        Log.d("tag", pass)
    }

    fun submitLogin(): Boolean {
        _uiState.update { currentState ->
            currentState.copy(user = "", pass = "")
        }
        return true
    }

}