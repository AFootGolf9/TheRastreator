package com.example.therastreator.ui

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.example.therastreator.data.ConfigRepository
import com.example.therastreator.data.LoginJson
import com.example.therastreator.data.WorkManagerSendRepository
import com.example.therastreator.network.LoginApi
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

    fun doFirst(context: Context): Boolean {
        configRepository = ConfigRepository(context.dataStore)
        runBlocking {
            _uiState.update { currentState ->
                currentState.copy(activated = configRepository.isOn.first())
            }
        }
        sendRepository = WorkManagerSendRepository(context)

        return runBlocking {
            return@runBlocking !configRepository.token.first().equals("")
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

    fun changeUser(user: String) {
        _uiState.update { currentState ->
            currentState.copy(user = user)
        }
    }

    fun changeEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }

    fun changePass(pass: String) {
        _uiState.update { currentState ->
            currentState.copy(pass = pass)
        }
        Log.d("tag", pass)
    }

    fun submitLogin(): Boolean {

        val loginInfo = LoginJson(user = _uiState.value.user, pass = _uiState.value.pass)
        var response: LoginJson

        try {
            runBlocking {
                response = LoginApi.retrofitService.login(loginInfo)
            }
        } catch (e: Exception) {
//            do nothing
            response = LoginJson(error = "yes")
        }

        if (response.error != null) {
            _uiState.update { currentState ->
                currentState.copy(fail = true)
            }
            return false
        }

        val token: String = response.token!!

        runBlocking {
            configRepository.saveToken(token)
        }

        _uiState.update { currentState ->
            currentState.copy(user = "", pass = "")
        }
        return true
    }

    fun submitRegister(): Boolean {

        val userInfo = LoginJson(
            user = _uiState.value.user,
            email = _uiState.value.email,
            pass = _uiState.value.pass
        )

        val response: LoginJson
        runBlocking {
            response = LoginApi.retrofitService.create(userInfo)
        }

        if (response.error != null) {
            return false
        }

        return true
    }

}