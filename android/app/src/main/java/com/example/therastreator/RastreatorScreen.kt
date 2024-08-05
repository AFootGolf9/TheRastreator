package com.example.therastreator

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.therastreator.ui.AppViewModel
import com.example.therastreator.ui.LoginScreen
import com.example.therastreator.ui.RegisterScreen
import com.example.therastreator.ui.SelectScreen

enum class RastreatorScreen(@StringRes val title: Int) {
    Login(title = R.string.loginOp),
    Select(title = R.string.app_name),
    Register(title = R.string.register)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RastreatorAppBar(
    currentScreen: RastreatorScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title))},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun RastreatorApp(
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val start = viewModel.doFirst(LocalContext.current)
    val backStackEntry by  navController.currentBackStackEntryAsState()

    val currentScreen = RastreatorScreen.valueOf(
        backStackEntry?.destination?.route ?: RastreatorScreen.Select.name
    )

    Scaffold(
        topBar = {
            RastreatorAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        val sLocation = if (start) {
            RastreatorScreen.Select.name
        } else {
            RastreatorScreen.Login.name
        }

        NavHost(
            navController = navController,
            startDestination = sLocation,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = RastreatorScreen.Select.name) {
                SelectScreen(
                    b = uiState.activated,
                    buttonPress = { viewModel.changeActivated() },
                    logOf = {
                        viewModel.endSession()
                        navController.navigate(RastreatorScreen.Login.name)
                        navController.clearBackStack(RastreatorScreen.Select.name)
                    }
                )
            }

            composable(route = RastreatorScreen.Register.name) {
                RegisterScreen(
                    uiState = uiState,
                    changeUser = { tx -> viewModel.changeUser(tx)},
                    changeEmail = { tx -> viewModel.changeEmail(tx)},
                    changePass = { tx -> viewModel.changePass(tx)},
                    submit = { if (viewModel.submitRegister()) {
                                navController.navigateUp()
                             }
                    }
                )
            }

            composable(route = RastreatorScreen.Login.name) {
                LoginScreen(
                    uiState,
                    { tx -> viewModel.changeUser(tx) },
                    { tx -> viewModel.changePass(tx) },
                    {
                        if (viewModel.submitLogin()){
                            navController.navigate(RastreatorScreen.Select.name)
                        }
                    },
                    {
                        viewModel.eraseFail()
                        navController.navigate(RastreatorScreen.Register.name)
                    }
                )
            }
        }
    }
}

object confRep {

}
