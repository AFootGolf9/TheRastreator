package com.example.therastreator.data

import android.content.Context

class AppContainer(context: Context) {
    val  sendRepository = WorkManagerSendRepository(context)
}