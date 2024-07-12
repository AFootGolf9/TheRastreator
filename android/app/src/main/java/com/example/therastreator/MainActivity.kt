package com.example.therastreator

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.therastreator.data.AppContainer
import com.example.therastreator.data.ConfigRepository
import com.example.therastreator.ui.theme.TheRastreatorTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

lateinit var container: AppContainer

private const val CONFIG_NAME = "user_config"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = CONFIG_NAME
)
lateinit var configRepository: ConfigRepository

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configRepository = ConfigRepository(dataStore)


        enableEdgeToEdge()
        setContent {
            TheRastreatorTheme {
                var b: Boolean
                runBlocking {
                    b = configRepository.isOn.first()
                }
                Screen(b)
            }
        }

        container = AppContainer(this)
    }
}

fun buttonPress(b: Boolean) {
    if(b){
        container.sendRepository.start()
    }else {
        container.sendRepository.stop()
    }
    runBlocking {
        configRepository.savePreference(b)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun Screen(b: Boolean = false) {
    Column {
        UpperBar()
        AppBody(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
        b = b)
    }
}

@Composable
fun UpperBar(modifier: Modifier = Modifier) {
    Surface(color = Color.Blue, modifier = modifier.fillMaxWidth()) {
        Column {
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 36.sp,
                modifier = modifier.padding(8.dp),
            )
        }
    }
}

@Composable
fun AppBody(modifier: Modifier = Modifier, b: Boolean) {
    var state by remember { mutableStateOf(b) }

    val color = when (state) {
        false -> Color.Red
        true -> Color.Green
    }

    val text = when (state) {
        false -> stringResource(R.string.off)
        true -> stringResource(R.string.on)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            state = !state
            buttonPress(state)
        },
            colors = ButtonColors(color, Color.White, Color.LightGray, Color.DarkGray)
        ) {
            Text(stringResource(R.string.turn))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontSize = 20.sp)
    }
}
