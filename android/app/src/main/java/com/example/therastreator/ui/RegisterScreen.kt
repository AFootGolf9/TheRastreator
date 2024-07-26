package com.example.therastreator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true,
    showBackground = true)
@Composable
fun RegisterScreen(
    uiState: AppUiState = AppUiState(user = "user", email = "email@a.con", pass = "pass", fail = true),
    changeUser: (String) -> Unit = {},
    changeEmail: (String) -> Unit = {},
    changePass: (String) -> Unit = {},
    submit: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(15.dp)
            ) {
                InputLine(
                    tipe = "User",
                    content = uiState.user,
                    action = changeUser,
                    modifier = modifier.fillMaxWidth()
                )
                InputLine(
                    tipe = "Email",
                    content = uiState.email,
                    action = changeEmail,
                    modifier = modifier.fillMaxWidth()
                )
                InputLine(
                    tipe = "Password",
                    content = uiState.pass,
                    action = changePass,
                    modifier = modifier.fillMaxWidth()
                )

                if (uiState.fail) {
                    Text(text = "user or password wrong",
                        color = Color.Red)
                }

                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = submit,
                    shape = ButtonDefaults.elevatedShape,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "submit")
                }
            }
        }
    }
}