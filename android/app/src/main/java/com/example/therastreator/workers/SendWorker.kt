package com.example.therastreator.workers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.therastreator.data.LocationJson
import com.example.therastreator.data.WorkManagerSendRepository
import com.example.therastreator.data.confRep
import com.example.therastreator.network.SendApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SendWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SendWorker"
    }

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    private val sendRepository = WorkManagerSendRepository(context)

    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }

        val token: String
        runBlocking {
            token = confRep.configRepository.token.first()
        }

        if (token.equals("")) {
            sendRepository.stop()
        }

        val task = locationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token,
        )
        Tasks.await(task)
        val location = task.result
        SendApi.retrofitService
            .postLocation(LocationJson(token, location.latitude, location.longitude, null))
        return Result.success()
    }


//    private fun startRep() {
//        configRepository = ConfigRepository()
//    }
}