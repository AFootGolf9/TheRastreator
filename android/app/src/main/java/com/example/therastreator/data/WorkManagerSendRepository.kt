package com.example.therastreator.data

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.therastreator.workers.SendWorker
import java.time.Duration
import java.util.concurrent.TimeUnit

class WorkManagerSendRepository(context: Context) {
    private val workManager = WorkManager.getInstance(context)

    public fun start() {
        val sendBuilder = PeriodicWorkRequestBuilder<SendWorker>(10, TimeUnit.SECONDS)
            .addTag("sender")

        workManager.enqueue(sendBuilder.build())
    }

    public fun stop() {
        workManager.cancelAllWorkByTag("sender")
    }
}