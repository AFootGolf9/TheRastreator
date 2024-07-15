package com.example.therastreator.ui

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
import com.example.therastreator.R


@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun SelectScreen(b: Boolean = false) {
    AppBody(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(),
        b = b)
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

    val color = when (b) {
        false -> Color.Red
        true -> Color.Green
    }

    val text = when (b) {
        false -> stringResource(R.string.off)
        true -> stringResource(R.string.on)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {

        },
            colors = ButtonColors(color, Color.White, Color.LightGray, Color.DarkGray)
        ) {
            Text(stringResource(R.string.turn))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontSize = 20.sp)
    }
}
