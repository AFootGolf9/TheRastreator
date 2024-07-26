package com.example.therastreator.ui

data class AppUiState(
    val activated: Boolean = false,
    val fail: Boolean = false,
    val user: String = "",
    val email: String = "",
    val pass: String = ""
)